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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {
    private static final int PAGE_SIZE = 9; // ✅ 공통 상수로 추출

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

    // HomeService 클래스 내부에 추가
    private java.util.Set<String> parseCsv(String csv) {
        if (csv == null || csv.trim().isEmpty()) return java.util.Collections.emptySet();
        java.util.Set<String> out = new java.util.LinkedHashSet<>();
        for (String token : csv.split(",")) {
            if (token == null) continue;
            String t = token.trim();
            if (!t.isEmpty()) out.add(t.toLowerCase(java.util.Locale.ROOT));
        }
        return out;
    }

    private boolean inSetOrAll(String value, java.util.Set<String> set) {
        if (set == null || set.isEmpty()) return true; // 필터 미적용
        if (value == null) return false;
        return set.contains(value.trim().toLowerCase(java.util.Locale.ROOT));
    }

    // ✅ (교체) 다중 필터 버전으로 대체
    public List<PartnershipInfoDto> getPartnershipsFiltered(
            String organizationCsv, String categoryCsv, String typeCsv,
            String sortBy, int page
    ) {
        final int safePage = Math.max(1, page);
        final int start = (safePage - 1) * PAGE_SIZE;

        Set<String> orgSet = parseCsv(organizationCsv);
        Set<String> catSet = parseCsv(categoryCsv);
        Set<String> typeSet = parseCsv(typeCsv);

        return partnershipRepository.findAll().stream()
                .filter(p -> {
                    String org  = (p.getStore() != null) ? p.getStore().getOrganization() : null;
                    String cat  = (p.getStore() != null) ? p.getStore().getCategory() : null;
                    String type = p.getType();
                    return inSetOrAll(org, orgSet)
                            && inSetOrAll(cat, catSet)
                            && inSetOrAll(type, typeSet);
                })
                .sorted(getComparator(sortBy))
                .skip(start)
                .limit(PAGE_SIZE)
                .map(partnershipMapper::toInfo)
                .toList();
    }


    // HomeService.java (추가) : KaKaoMapService 호출용
    public List<PartnershipInfoDto> getSortedPartnershipsByCategoryAll(String category, String sortBy) {
        return partnershipRepository.findAll().stream()
                .filter(p -> filterByCategory(p, category))
                .sorted(getComparator(sortBy))
                .map(partnershipMapper::toInfo)
                .collect(Collectors.toList());
    }

    /** ✅ 총 페이지 수 계산 (organization/category/type 동시 필터) */
    public int getPageAmount(String organizationCsv, String categoryCsv, String typeCsv) {
        java.util.Set<String> orgSet  = parseCsv(organizationCsv);
        java.util.Set<String> catSet  = parseCsv(categoryCsv);
        java.util.Set<String> typeSet = parseCsv(typeCsv);

        long totalCount = partnershipRepository.findAll().stream()
                .filter(p -> {
                    String org  = (p.getStore() != null) ? p.getStore().getOrganization() : null;
                    String cat  = (p.getStore() != null) ? p.getStore().getCategory() : null;
                    String type = p.getType();
                    return inSetOrAll(org, orgSet)
                            && inSetOrAll(cat, catSet)
                            && inSetOrAll(type, typeSet);
                })
                .count();

        return (int) Math.ceil(totalCount / (double) PAGE_SIZE);
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
