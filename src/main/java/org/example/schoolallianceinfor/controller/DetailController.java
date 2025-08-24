package org.example.schoolallianceinfor.controller;

import org.example.schoolallianceinfor.entity.Partnership;
import org.example.schoolallianceinfor.entity.Review;
import org.example.schoolallianceinfor.repository.PartnershipRepository;
import org.example.schoolallianceinfor.repository.ReviewRepository;
import org.example.schoolallianceinfor.service.DetailService;         // ✅ 얇은 컨트롤러: 서비스로 위임
import org.example.schoolallianceinfor.service.FileStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;         // spring 트랜잭션
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/partnership-info/detail")
public class DetailController {

    private final ReviewRepository reviewRepository;
    private final PartnershipRepository partnershipRepository;
    private final FileStorageService fileStorageService;
    private final DetailService detailService; // ✅ 추가

    public DetailController(ReviewRepository reviewRepository,
                            PartnershipRepository partnershipRepository,
                            FileStorageService fileStorageService,
                            DetailService detailService) {           // ✅ 주입
        this.reviewRepository = reviewRepository;
        this.partnershipRepository = partnershipRepository;
        this.fileStorageService = fileStorageService;
        this.detailService = detailService;
    }

    /**
     * POST /partnership-info/detail/{partnershipId}/review/post
     *  - receiptFile : 이미지 1개(필수)
     *  - photoFiles  : 이미지 0~3개(선택)
     *  - text        : 문자열(필수)
     */
    @PostMapping(
            value = "/{partnershipId}/review/post",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Transactional
    public ResponseEntity<Map<String, Object>> createReview(
            @PathVariable Integer partnershipId,
            @RequestPart("receiptFile") MultipartFile receiptFile,
            @RequestPart(value = "photoFiles", required = false) List<MultipartFile> photoFiles,
            @RequestPart("text") String text // text 바인딩 이슈 시 @RequestParam("text")로 변경 가능
    ) throws IOException {

        if (receiptFile == null || receiptFile.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "receiptFile(이미지 1개)은 필수입니다."));
        }
        if (photoFiles != null && photoFiles.size() > 3) {
            return ResponseEntity.badRequest().body(Map.of("message", "photoFiles는 0~3개까지만 허용됩니다."));
        }
        if (text == null || text.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "text는 비어 있을 수 없습니다."));
        }

        Partnership partnership = partnershipRepository.findById(partnershipId).orElse(null);
        if (partnership == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "유효하지 않은 partnershipId 입니다."));
        }

        String receiptUrl = fileStorageService.saveOne(receiptFile);
        List<String> photoUrls = new ArrayList<>();
        if (photoFiles != null) {
            for (MultipartFile f : photoFiles) {
                if (f != null && !f.isEmpty()) {
                    photoUrls.add(fileStorageService.saveOne(f));
                }
            }
        }

        Review review = Review.builder()
                .receiptUrl(receiptUrl)
                .photoUrl(photoUrls)
                .text(text)
                .partnership(partnership)
                .build();
        review = reviewRepository.save(review);

        return ResponseEntity.ok(Map.of(
                "reviewId", review.getReviewId(),
                "receiptUrl", receiptUrl,
                "photoUrl", photoUrls,
                "text", review.getText()
        ));
    }

    /**
     * GET /partnership-info/detail/{partnershipId}/review
     * - 요약(summary) + ReviewSimpleDto 리스트(items)를 Map으로 반환
     *   (컨트롤러는 얇게: 서비스로 위임)
     */
    @GetMapping(value = "/{partnershipId}/review", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getPartnershipReviews(@PathVariable Integer partnershipId) {
        return ResponseEntity.ok(detailService.getPartnershipReviewsPayload(partnershipId));
    }

    /**
     * GET /partnership-info/detail/{partnershipId}/inform
     * - target/type/날짜들/note를 JSON으로 반환
     *   (컨트롤러는 얇게: 서비스로 위임)
     */
    @GetMapping(value = "/{partnershipId}/inform", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getPartnershipInform(@PathVariable Integer partnershipId) {
        return detailService.getPartnershipInformPayload(partnershipId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
