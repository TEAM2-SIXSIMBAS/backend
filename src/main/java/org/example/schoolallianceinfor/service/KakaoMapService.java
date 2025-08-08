package org.example.schoolallianceinfor.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.example.schoolallianceinfor.dto.StoreMarkerDto;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class KakaoMapService {

    private static final String KAKAO_API_KEY = "f103bcadc4c1aec46255de2d35373895";  // 너의 REST API 키
    private static final String KAKAO_MAP_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public double[] getCoordinatesFromAddress(String address) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // 한글 주소 인코딩
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
            URI uri = new URI(KAKAO_MAP_URL + "?query=" + encodedAddress);

            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            // API 요청
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            // 응답 파싱
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray documents = jsonObject.getJSONArray("documents");

            if (documents.length() > 0) {
                JSONObject location = documents.getJSONObject(0);
                double latitude = location.getDouble("y");
                double longitude = location.getDouble("x");
                return new double[]{latitude, longitude};
            } else {
                // 주소 결과가 없을 때
                return new double[]{0.0, 0.0};
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new double[]{0.0, 0.0};  // 예외 발생 시 기본 좌표 반환
        }

    }
    public List<StoreMarkerDto> getAllStoreMarkers() {
        // 🧪 더미 가게 주소 리스트 (DB 연동 전까지 임시 사용)
        List<String> storeNames = List.of("김밥천국", "한솥도시락");
        List<String> storeAddresses = List.of("서울특별시 강남구 역삼로 123", "서울특별시 관악구 봉천동 456");

        List<StoreMarkerDto> markers = new ArrayList<>();

        for (int i = 0; i < storeNames.size(); i++) {
            double[] coords = getCoordinatesFromAddress(storeAddresses.get(i));
            StoreMarkerDto marker = new StoreMarkerDto(storeNames.get(i), coords[0], coords[1]);
            markers.add(marker);
        }

        return markers;
    }
}
