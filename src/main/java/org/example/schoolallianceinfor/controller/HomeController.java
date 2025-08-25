// src/main/java/org/example/schoolallianceinfor/controller/HomeController.java
package org.example.schoolallianceinfor.controller;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.partnership.PartnershipInfoDto;
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
     * 예시:
     * GET /partnership-info?organization=총동,컴공&category=카페,식당&type=혜택,이벤트&page=1&sort=views
     *
     * sort (대소문자 무관):
     * views | discountRate | saleStartDate | saleEndDate | useStartDate | useEndDate
     *
     * - organization/category/type 는 콤마(,)로 여러 값 전달 가능 (서비스에서 파싱)
     * - null/빈값이면 해당 필터 미적용 → 전체 대상
     * - page 기본값 1
     * - sort 파라미터는 URL의 마지막에 두는 컨벤션을 따름
     */
    @GetMapping
    public Map<String, Object> getHomePartnerships(
            @RequestParam(required = false) String organization,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "views") String sort
    ) {
        List<PartnershipInfoDto> top3 = homeService.getTop3ByViews();

        // 서비스가 콤마 분리/트리밍/대소문자 정규화/빈값 무시 등을 담당
        List<PartnershipInfoDto> sorted = homeService.getPartnershipsFiltered(
                organization, category, type, sort, page
        );

        int pageAmount = homeService.getPageAmount(organization, category, type);

        return Map.of(
                "top3", top3,
                "sort", sorted,
                "pageAmount", pageAmount
        );
    }
}
