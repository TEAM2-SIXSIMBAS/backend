// src/main/java/org/example/schoolallianceinfor/controller/StoreController.java
package org.example.schoolallianceinfor.controller;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.store.StoreInfoDto;
import org.example.schoolallianceinfor.service.StoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store-info")
public class StoreController {

    private final StoreService storeService;

    /** 페이지 목록: { "list": [...] } */
    @GetMapping
    public Map<String, List<StoreInfoDto>> getStoreInfoPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return storeService.getPageEnvelope(page, size);
    }

    /** 단건: DTO 없이 6개 필드만 */
    @GetMapping("/{storeId}")
    public Map<String, Object> getStoreInfo(@PathVariable Integer storeId) {
        return storeService.getStoreSummaryMap(storeId);
    }

    /** 검색: /store-info/search?storeName={검색어}&page={페이지}
     *  부분일치(대소문자 무시), size=10 고정, 응답 { "list": List<StoreInfoDto> } */
    @GetMapping("/search")
    public Map<String, List<StoreInfoDto>> searchStores(
            @RequestParam String storeName,
            @RequestParam(defaultValue = "1") int page
    ) {
        List<StoreInfoDto> list = storeService.searchByStoreName(storeName, page);
        return Map.of("list", list);
    }
}
