package org.example.schoolallianceinfor.service;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.PartnershipInfoDto;
import org.example.schoolallianceinfor.entity.Partnership;
import org.example.schoolallianceinfor.repository.PartnershipRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final PartnershipRepository partnershipRepository;

    // 조회수 기준 상위 3개
    public List<PartnershipInfoDto> getTop3ByViews() {
        return partnershipRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Partnership::getViews).reversed())
                .limit(3)
                .map(PartnershipInfoDto::new)
                .collect(Collectors.toList());
    }

    // 필터링 + 정렬
    public List<PartnershipInfoDto> getSortedPartnershipsByCategory(String category, String sortBy, int page) {
        int pageSize = 9;
        int start = (page-1) * pageSize;

        return partnershipRepository.findAll().stream()
                .filter(p -> category == null ||
                        (p.getStore() != null && category.equalsIgnoreCase(p.getStore().getCategory())))
                .sorted(getComparator(sortBy))
                .skip(start)
                .limit(pageSize)
                .map(PartnershipInfoDto::new)
                .collect(Collectors.toList());

    }

    // 정렬 기준 선택
    private Comparator<Partnership> getComparator(String sortBy) {
        if ("views".equalsIgnoreCase(sortBy)) {
            return Comparator.comparingInt(Partnership::getViews).reversed(); // 조회수 많은 순
        } else if ("discountRate".equalsIgnoreCase(sortBy)) {
            return Comparator.comparingInt(Partnership::getDiscountRate).reversed(); // 할인률 높은 순
        } else if ("saleStartDate".equalsIgnoreCase(sortBy)) {
            return Comparator.comparing(Partnership::getSaleStartDate); // 할인 시작일 빠른 순
        } else if ("saleEndDate".equalsIgnoreCase(sortBy)) {
            return Comparator.comparing(Partnership::getSaleEndDate); // 할인 종료일 빠른 순
        } else if ("useStartDate".equalsIgnoreCase(sortBy)) {
            return Comparator.comparing(Partnership::getUseStartDate); // 사용 시작일 빠른 순
        } else if ("useEndDate".equalsIgnoreCase(sortBy)) {
            return Comparator.comparing(Partnership::getUseEndDate); // 사용 종료일 빠른 순
        } else {
            // 기본 정렬: 조회수 많은 순
            return Comparator.comparingInt(Partnership::getViews).reversed();
        }
    }
}
