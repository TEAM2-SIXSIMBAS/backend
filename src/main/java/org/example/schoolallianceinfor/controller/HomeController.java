package org.example.schoolallianceinfor.controller;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.PartnershipInfoDto;
import org.example.schoolallianceinfor.service.HomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partnership-info")
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    public Map<String, List<PartnershipInfoDto>> getHomePartnerships(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "views") String sort,
            @RequestParam(defaultValue = "1") int page
    ) {
        // 조회수 상위 3개
        List<PartnershipInfoDto> top3 = homeService.getTop3ByViews();

        // 카테고리별 정렬된 리스트
        List<PartnershipInfoDto> sorted = homeService.getSortedPartnershipsByCategory(category, sort, page);

        return Map.of(
                "top3", top3,
                "sort", sorted
        );
    }
}
