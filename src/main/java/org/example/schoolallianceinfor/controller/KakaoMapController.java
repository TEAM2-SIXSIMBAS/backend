package org.example.schoolallianceinfor.controller;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.CoordinateDto;
import org.example.schoolallianceinfor.dto.StoreMarkerDto;

import org.example.schoolallianceinfor.service.KakaoMapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class KakaoMapController {

    private final KakaoMapService kakaoMapService;

    // ✅ 주소 → 좌표 반환 API
    @GetMapping("/coordinates")
    public ResponseEntity<CoordinateDto> getCoordinates(@RequestParam String address) {
        double[] coords = kakaoMapService.getCoordinatesFromAddress(address);
        CoordinateDto result = new CoordinateDto(coords[0], coords[1]);
        return ResponseEntity.ok(result);
    }
    // ✅ 여러 매장 위치(마커용) 반환
    @GetMapping("/markers")
    public List<StoreMarkerDto> getAllStoreMarkers() {
        return kakaoMapService.getAllStoreMarkers();
    }
}
