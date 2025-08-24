// src/main/java/org/example/schoolallianceinfor/service/StoreService.java
package org.example.schoolallianceinfor.service;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.store.StoreInfoDto;
import org.example.schoolallianceinfor.entity.Store;
import org.example.schoolallianceinfor.mapper.StoreMapper;
import org.example.schoolallianceinfor.repository.StoreRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {
    private static final int SEARCH_PAGE_SIZE = 6;   // 🔒 검색 페이지 사이즈 고정

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper; // 이미 보유하고 있는 매퍼 사용

    /** 페이지 목록: { "list": [...] } (1-based page, 정렬: storeName ASC) */
    /** 페이지 목록: { "list": [...] } (1-based page, 정렬: storeName ASC) */
    public List<StoreInfoDto> getPageEnvelope(int page) {
        int p = Math.max(0, page - 1); // 0-base 인덱스

        Page<Store> data = storeRepository.findAll(
                PageRequest.of(p, SEARCH_PAGE_SIZE, Sort.by(Sort.Direction.ASC, "storeName"))
        );

        List<StoreInfoDto> list = data.getContent().stream() // ✅ getContent() 사용
                .map(storeMapper::toInfo)
                .toList();

        return list;
    }

    /** 단건 요약: DTO 없이 6개 필드만 반환 */
    public Map<String, Object> getStoreSummaryMap(Integer storeId) {
        Store st = storeRepository.findById(storeId)
                .orElseThrow(() -> new NoSuchElementException("Store not found: " + storeId));

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("storeName", st.getStoreName());
        result.put("Number", st.getPhoneNumber()); // 요구사항 키 그대로 'Number'
        result.put("email", st.getEmail());
        result.put("address", st.getAddress());
        result.put("openTime", st.getOpenTime());
        result.put("closeTime", st.getCloseTime());
        return result;
    }

    /** 검색: storeName 부분일치(대소문자 무시), size=10 고정, 정렬: storeName ASC */
    public List<StoreInfoDto> searchByStoreName(String storeName, int page) {
        if (storeName == null || storeName.isBlank()) return List.of();
        int p = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(p, SEARCH_PAGE_SIZE, Sort.by(Sort.Direction.ASC, "storeName"));

        return storeRepository
                .findByStoreNameContainingIgnoreCase(storeName.trim(), pageable)
                .stream()
                .map(storeMapper::toInfo)
                .collect(Collectors.toList());
    }

    /** 🔢 검색 총 페이지 수 반환 (컨트롤러에서 pageAmount로 사용) */
    public int getStoreNameSearchPageAmount(String storeName) {
        Pageable pageable = PageRequest.of(
                0,
                SEARCH_PAGE_SIZE,
                Sort.by(Sort.Direction.ASC, "storeName")
        );

        Page<Store> result;

        if (storeName == null || storeName.isBlank()) {
            // 전체 조회
            result = storeRepository.findAll(pageable);
        } else {
            // 부분일치 검색
            result = storeRepository.findByStoreNameContainingIgnoreCase(storeName.trim(), pageable);
        }

        return result.getTotalPages();
    }

}
