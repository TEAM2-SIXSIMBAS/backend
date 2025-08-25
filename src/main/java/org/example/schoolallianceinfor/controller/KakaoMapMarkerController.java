// src/main/java/org/example/schoolallianceinfor/controller/KakaoMapMarkerController.java
package org.example.schoolallianceinfor.controller;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.store.StoreMarkerDto;
import org.example.schoolallianceinfor.service.KakaoMapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KakaoMapMarkerController {

    private final KakaoMapService kakaoMapService;

    /**
     * 예) GET /partnership-map?category=food&sort=views
     * - 페이지 파라미터 없음(전체 결과)
     * - sort: views | discountRate | storeName (대소문자 무관)
     * - 반환: 마커 좌표 리스트 (lat/lng)
     */
    @GetMapping("/partnership-map")
    public ResponseEntity<List<StoreMarkerDto>> getMarkers(
            @RequestParam(required = false) String category,
            @RequestParam(name = "sort", defaultValue = "views") String sort
    ) {
        List<StoreMarkerDto> markers = kakaoMapService.getMarkersByHomeCriteria(category, sort);
        return ResponseEntity.ok(markers);
    }
}
