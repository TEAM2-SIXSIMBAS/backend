// src/main/java/org/example/schoolallianceinfor/service/KakaoMapService.java
package org.example.schoolallianceinfor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.schoolallianceinfor.dto.partnership.PartnershipInfoDto;
import org.example.schoolallianceinfor.dto.store.StoreMarkerDto;
import org.example.schoolallianceinfor.entity.Store;
import org.example.schoolallianceinfor.repository.StoreRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoMapService {

    private final HomeService homeService;          // 홈 서비스 결과 활용
    private final StoreRepository storeRepository;  // Store ↔ StoreLocation 조회
    private final RestTemplateBuilder rtBuilder;    // 타임아웃 설정 위해 주입

    // lazy 캐시된 RestTemplate (한 번만 생성 후 재사용)
    private volatile RestTemplate cachedRt;

    // 환경 변수/설정 파일에서 주입 (application.yml/properties)
    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Value("${kakao.api.url.address:https://dapi.kakao.com/v2/local/search/address.json}")
    private String addressSearchUrl;

    @Value("${kakao.api.url.keyword:https://dapi.kakao.com/v2/local/search/keyword.json}")
    private String keywordSearchUrl;

    /** 버전 호환 타임아웃 설정 (ms 단위) — deprecation 없음 */
    private RestTemplate rt() {
        RestTemplate local = cachedRt;
        if (local == null) {
            synchronized (this) {
                local = cachedRt;
                if (local == null) {
                    local = rtBuilder
                            .requestFactory(() -> {
                                SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
                                f.setConnectTimeout(3000); // 3초
                                f.setReadTimeout(3000);    // 3초
                                return f;
                            })
                            .build();
                    cachedRt = local;
                }
            }
        }
        return local;
    }

    private HttpHeaders kakaoHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    /* ─────────────────────────────────────────────────────────────
     * B) 카카오 지오코딩 (주소/가게명 → 좌표)
     *    성공: [lat, lng], 실패: [0.0, 0.0]
     * ───────────────────────────────────────────────────────────── */

    public double[] getCoordinatesFromAddress(String address) {
        if (address == null || address.isBlank()) return new double[]{0.0, 0.0};
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(addressSearchUrl)
                    .queryParam("query", address)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUri();

            ResponseEntity<String> res = rt().exchange(
                    uri, HttpMethod.GET, new HttpEntity<>(kakaoHeaders()), String.class);

            if (res.getStatusCode().is2xxSuccessful() && res.getBody() != null) {
                JSONObject json = new JSONObject(res.getBody());
                JSONArray docs = json.optJSONArray("documents");
                if (docs != null && docs.length() > 0) {
                    JSONObject d0 = docs.getJSONObject(0);
                    // Kakao Local API: x=lng, y=lat
                    return new double[]{ d0.getDouble("y"), d0.getDouble("x") }; // (lat, lng)
                }
            }
        } catch (RestClientException e) {
            log.warn("Kakao address geocoding failed: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Kakao address geocoding unexpected error", e);
        }
        return new double[]{0.0, 0.0};
    }

    public double[] getCoordinatesFromName(String name) {
        if (name == null || name.isBlank()) return new double[]{0.0, 0.0};
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(keywordSearchUrl)
                    .queryParam("query", name)
                    .queryParam("size", 1)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUri();

            ResponseEntity<String> res = rt().exchange(
                    uri, HttpMethod.GET, new HttpEntity<>(kakaoHeaders()), String.class);

            if (res.getStatusCode().is2xxSuccessful() && res.getBody() != null) {
                JSONObject json = new JSONObject(res.getBody());
                JSONArray docs = json.optJSONArray("documents");
                if (docs != null && docs.length() > 0) {
                    JSONObject d0 = docs.getJSONObject(0);
                    return new double[]{ d0.getDouble("y"), d0.getDouble("x") }; // (lat, lng)
                }
            }
        } catch (RestClientException e) {
            log.warn("Kakao keyword geocoding failed: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Kakao keyword geocoding unexpected error", e);
        }
        return new double[]{0.0, 0.0};
    }

    /** 주소 우선, 실패 시 가게명으로 폴백 */
    public double[] resolveCoordinate(String address, String storeName) {
        double[] byAddr = getCoordinatesFromAddress(address);
        if (isValidCoord(byAddr)) return byAddr;

        double[] byName = getCoordinatesFromName(storeName);
        if (isValidCoord(byName)) return byName;

        return new double[]{0.0, 0.0};
    }

    private boolean isValidCoord(double[] c) {
        if (c == null || c.length != 2) return false;
        double lat = c[0], lng = c[1];
        if (lat == 0.0 && lng == 0.0) return false;
        return lat >= -90 && lat <= 90 && lng >= -180 && lng <= 180;
    }

    /* ─────────────────────────────────────────────────────────────
     * A) 홈서비스 결과 활용 → StoreLocation 좌표로 마커 리스트
     *    (외부 API 호출 없음, 페이징 없음: 전체 반환)
     * ───────────────────────────────────────────────────────────── */
    @Transactional(readOnly = true)
    public List<StoreMarkerDto> getMarkersByHomeCriteria(String category, String sortBy) {
        log.info("[marker] input category={}, sort={}", category, sortBy);

        // 1) 홈서비스에서 전체 정렬 결과 가져오기
        List<PartnershipInfoDto> infos =
                homeService.getSortedPartnershipsByCategoryAll(category, sortBy);
        log.info("[marker] homeservice infos size={}", infos.size());

        // 2) 순서 보존 + 중복 제거된 가게명
        List<String> namesInOrder = infos.stream()
                .map(PartnershipInfoDto::getStoreName)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
        log.info("[marker] namesInOrder={}", namesInOrder);

        if (namesInOrder.isEmpty()) return List.of();

        // 3) 한 번에 Store 로딩(N+1 방지) — location은 @EntityGraph 추천
        List<Store> stores = storeRepository.findByStoreNameIn(namesInOrder);

        // 4) 이름 → Store 매핑 (입력 순서 보존)
        Map<String, Store> byName = stores.stream()
                .collect(Collectors.toMap(
                        Store::getStoreName,
                        s -> s,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        // 5) 정렬 순서 유지하며 좌표만 추출 (좌표 없으면 지오코딩 폴백)
        List<StoreMarkerDto> markers = new ArrayList<>();
        for (String name : namesInOrder) {
            Store s = byName.get(name);
            if (s == null) {
                log.warn("[marker] store not found by name='{}'", name);
                continue;
            }

            Double lat = null, lng = null;
            if (s.getLocation() != null) {
                lat = s.getLocation().getLat();
                lng = s.getLocation().getLng();
            }

            if (lat == null || lng == null) {
                log.info("[marker] geocoding '{}', addr='{}'", s.getStoreName(), s.getAddress());
                double[] c = resolveCoordinate(s.getAddress(), s.getStoreName());
                log.info("[marker] geocoded result: lat={}, lng={}", c[0], c[1]);
                if (isValidCoord(c)) {
                    lat = c[0];
                    lng = c[1];
                } else {
                    log.warn("[marker] geocoding failed for '{}'", s.getStoreName());
                    continue;
                }
            }

            markers.add(new StoreMarkerDto(lat, lng));
        }

        log.info("[marker] return markers size={}", markers.size());
        return markers;
    }
}
