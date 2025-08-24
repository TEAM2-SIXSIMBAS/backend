package org.example.schoolallianceinfor.controller;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.service.SummaryReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class SummaryReviewController {

    private final SummaryReviewService summaryReviewService;

    /** 특정 제휴ID의 리뷰들만 요약 — 항상 문자열로 반환 */
    @GetMapping("/summary/{partnershipId}")
    public String summarizeByPartnership(@PathVariable Integer partnershipId) {
        try {
            return summaryReviewService.summarizeFiveReviewsByPartnership(partnershipId);
        } catch (IllegalStateException e) {
            // 비즈니스 규칙(리뷰 < 5개 등) 실패 사유를 그대로 전달
            return "요약 실패: " + e.getMessage();
        } catch (Exception e) {
            // 예상치 못한 예외 방어
            return "요약 실패: 시스템 오류가 발생했습니다.";
        }
    }
}
