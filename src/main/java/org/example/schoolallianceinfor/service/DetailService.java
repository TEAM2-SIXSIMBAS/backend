package org.example.schoolallianceinfor.service;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.review.ReviewSimpleDto;
import org.example.schoolallianceinfor.entity.Partnership;
import org.example.schoolallianceinfor.entity.Review;
import org.example.schoolallianceinfor.repository.PartnershipRepository;
import org.example.schoolallianceinfor.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailService {

    private final ReviewRepository reviewRepository;
    private final PartnershipRepository partnershipRepository;
    private final SummaryReviewService summaryReviewService;

    /** 제휴(partnershipId) 기준: '요약' + ReviewSimpleDto 리스트를 Map으로 반환 */
    public Map<String, Object> getPartnershipReviewsPayload(Integer partnershipId) {
        // 1) 최신순 리뷰 조회
        List<Review> reviews =
                reviewRepository.findAllByPartnership_PartnershipIdOrderByReviewIdDesc(partnershipId);

        // 2) 한 줄 요약 (요약 서비스가 5개 미만도 자체 처리한다고 가정)
        String summary = summaryReviewService.summarizeFiveReviewsByPartnership(partnershipId);

        // 3) 엔티티 → SimpleDto 매핑 (영수증 URL 제외)
        List<ReviewSimpleDto> items = reviews.stream()
                .map(r -> ReviewSimpleDto.builder()
                        .photoUrl(r.getPhotoUrl())
                        .text(r.getText())
                        .build())
                .toList();

        // 4) Map으로 래핑
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("summary", summary);
        payload.put("items", items);
        return payload;
    }

    /** 제휴(partnershipId) 기준: inform 페이로드(Map) 생성 */
    public Optional<Map<String, Object>> getPartnershipInformPayload(Integer partnershipId) {
        return partnershipRepository.findById(partnershipId).map(this::toInformMap);
    }

    private Map<String, Object> toInformMap(Partnership p) {
        // Map.of는 null 값을 허용하지 않으므로 LinkedHashMap 사용
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("target", p.getTarget());
        m.put("type", p.getType());
        m.put("saleStartDate", p.getSaleStartDate()); // LocalDate → JSON: "yyyy-MM-dd"
        m.put("saleEndDate", p.getSaleEndDate());
        m.put("useStartDate", p.getUseStartDate());
        m.put("useEndDate", p.getUseEndDate());
        m.put("note", p.getNote());
        m.put("partnershipImageUrl", p.getPartnershipImageUrl());
        return m;
    }
}
