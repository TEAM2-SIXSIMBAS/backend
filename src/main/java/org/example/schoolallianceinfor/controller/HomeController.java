// src/main/java/org/example/schoolallianceinfor/controller/HomeController.java
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

    /**
     * 예) GET /partnership-info?category=food&sort=views&page=1
     * sort: views | discountRate | saleStartDate | saleEndDate | useStartDate | useEndDate (대소문자 무관)
     */
    @GetMapping
    public Map<String, List<PartnershipInfoDto>> getHomePartnerships(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "views") String sort,
            @RequestParam(defaultValue = "1") int page
    ) {
        List<PartnershipInfoDto> top3 = homeService.getTop3ByViews();
        List<PartnershipInfoDto> sorted = homeService.getSortedPartnershipsByCategory(category, sort, page);
        return Map.of("top3", top3, "sort", sorted);
    }
}
