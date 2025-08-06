package org.example.schoolallianceinfor.controller;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.ResponseDto;
import org.example.schoolallianceinfor.dto.TemporaryReviewDto;
import org.example.schoolallianceinfor.service.SummaryReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class SummaryReviewController {

    @PostMapping("/save")
    public ResponseEntity<?> saveReview(@RequestBody TemporaryReviewDto request) {
        try {
            summaryReviewService.saveReview(request.getText());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("저장 실패: " + e.getMessage());
        }
    }

    private final SummaryReviewService summaryReviewService;

    // ✅ 리뷰 5개 요약 요청 (GET)
    @GetMapping("/summary")
    public ResponseEntity<ResponseDto> summarizeReviews() {
        try {
            String summary = summaryReviewService.summarizeFiveReviews();
            return ResponseEntity.ok(new ResponseDto(summary));
        } catch (IllegalStateException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseDto("요약 실패: " + e.getMessage()));
        }
    }
}
