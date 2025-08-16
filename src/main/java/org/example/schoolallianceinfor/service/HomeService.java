// src/main/java/org/example/schoolallianceinfor/service/HomeService.java
package org.example.schoolallianceinfor.service;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.partnership.PartnershipInfoDto;
import org.example.schoolallianceinfor.entity.Partnership;
import org.example.schoolallianceinfor.mapper.PartnershipMapper;
import org.example.schoolallianceinfor.repository.PartnershipRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final PartnershipRepository partnershipRepository;
    private final PartnershipMapper partnershipMapper;

    /** 조회수 기준 상위 3개 */
    public List<PartnershipInfoDto> getTop3ByViews() {
        return partnershipRepository.findAll().stream()
                .sorted(Comparator.comparingInt((Partnership p) -> safeInt(p.getViews())).reversed())
                .limit(3)
                .map(partnershipMapper::toInfo)
                .collect(Collectors.toList());
    }


    /** 필터링 + 정렬 + 페이징 */
    public List<PartnershipInfoDto> getSortedPartnershipsByCategory(String category, String sortBy, int page) {
        final int pageSize = 9;
        final int start = Math.max(0, (page - 1)) * pageSize;

        return partnershipRepository.findAll().stream()
                .filter(p -> filterByCategory(p, category))
                .sorted(getComparator(sortBy))
                .skip(start)
                .limit(pageSize)
                .map(partnershipMapper::toInfo) // 🔁 엔티티→Info DTO 매퍼 호출
                .collect(Collectors.toList());
    }

    // HomeService.java (추가)
    public List<PartnershipInfoDto> getSortedPartnershipsByCategoryAll(String category, String sortBy) {
        return partnershipRepository.findAll().stream()
                .filter(p -> filterByCategory(p, category))
                .sorted(getComparator(sortBy))
                .map(partnershipMapper::toInfo)
                .collect(Collectors.toList());
    }

    /* ====================== 내부 유틸 ====================== */

    private boolean filterByCategory(Partnership p, String category) {
        if (category == null || category.isBlank()) return true;
        if (p.getStore() == null || p.getStore().getCategory() == null) return false;
        return category.equalsIgnoreCase(p.getStore().getCategory());
    }

    private Comparator<Partnership> getComparator(String sortBy) {
        final String key = (sortBy == null) ? "views" : sortBy.toLowerCase();

        switch (key) {
            case "views":
                return Comparator.comparingInt((Partnership p) -> safeInt(p.getViews())).reversed();
            case "discountrate":
                return Comparator.comparingInt((Partnership p) -> safeInt(p.getDiscountRate())).reversed();
            case "salestartdate":
                return comparingDate(Partnership::getSaleStartDate, false);
            case "saleenddate":
                return comparingDate(Partnership::getSaleEndDate, false);
            case "usestartdate":
                return comparingDate(Partnership::getUseStartDate, false);
            case "useenddate":
                return comparingDate(Partnership::getUseEndDate, false);
            default:
                // 기본: 조회수 많은 순
                return Comparator.comparingInt((Partnership p) -> safeInt(p.getViews())).reversed();
        }
    }

    private int safeInt(Integer v) {
        return (v == null) ? 0 : v;
    }

    private Comparator<Partnership> comparingDate(java.util.function.Function<Partnership, LocalDate> getter,
                                                  boolean desc) {
        Comparator<Partnership> cmp =
                Comparator.comparing(getter, Comparator.nullsLast(Comparator.naturalOrder()));
        return desc ? cmp.reversed() : cmp;
    }
}
