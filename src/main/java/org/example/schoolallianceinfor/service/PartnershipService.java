package org.example.schoolallianceinfor.service;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.domain.Partnership;
import org.example.schoolallianceinfor.dto.PartnershipRequestDto;
import org.example.schoolallianceinfor.dto.PartnershipSearchCondition;
import org.example.schoolallianceinfor.repository.PartnershipRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnershipService {

    private final PartnershipRepository partnershipRepository;

    // ✅ DTO 기반 저장
    public Partnership save(PartnershipRequestDto requestDto) {
        Partnership partnership = new Partnership();

        partnership.setStoreName(requestDto.getStoreName());
        partnership.setContent(requestDto.getContent());
        partnership.setTarget(requestDto.getTarget());
        partnership.setType(requestDto.getType());
        partnership.setDiscountRate(requestDto.getDiscountRate());
        partnership.setSaleStartDate(LocalDate.parse(requestDto.getSaleStartDate()));
        partnership.setSaleEndDate(LocalDate.parse(requestDto.getSaleEndDate()));
        partnership.setUseStartDate(LocalDate.parse(requestDto.getUseStartDate()));
        partnership.setUseEndDate(LocalDate.parse(requestDto.getUseEndDate()));
        partnership.setNote(requestDto.getNote());
        partnership.setViews(0); // 최초 생성 시 조회수 0

        return partnershipRepository.save(partnership);
    }

    // ✅ 전체 조회
    public List<Partnership> findAll() {
        return partnershipRepository.findAll();
    }

    // ✅ 단건 조회
    public Partnership findById(Integer id) {
        return partnershipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 제휴 정보가 없습니다: " + id));
    }

    // ✅ 조건 검색 + 정렬 기능
    public List<Partnership> search(PartnershipSearchCondition condition) {
        return partnershipRepository.findAll().stream()
                .filter(p -> condition.getCategory() == null || p.getType().equals(condition.getCategory()))
                .filter(p -> condition.getOrganization() == null || p.getStoreName().contains(condition.getOrganization()))
                .filter(p -> condition.getTarget() == null || p.getTarget().equals(condition.getTarget()))
                .sorted(getComparator(condition.getSortBy()))
                .collect(Collectors.toList());
    }

    // ✅ 정렬 조건 처리
    private Comparator<Partnership> getComparator(String sortBy) {
        if ("discountRate".equalsIgnoreCase(sortBy)) {
            return Comparator.comparingInt(Partnership::getDiscountRate).reversed(); // 높은 순
        } else if ("views".equalsIgnoreCase(sortBy)) {
            return Comparator.comparingInt(Partnership::getViews).reversed();
        } else if ("storeName".equalsIgnoreCase(sortBy)) {
            return Comparator.comparing(Partnership::getStoreName);
        } else {
            return (p1, p2) -> 0; // 정렬 없이 그대로
        }
    }
}
