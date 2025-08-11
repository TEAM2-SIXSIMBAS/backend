package org.example.schoolallianceinfor.controller;

import org.example.schoolallianceinfor.dto.ReviewSimpleDto;
import org.example.schoolallianceinfor.service.PartnershipService;
import org.example.schoolallianceinfor.service.ReviewQueryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/partnership-info")
public class PartnershipInfoController {
    private final ReviewQueryService reviewQueryService;

    public PartnershipInfoController(ReviewQueryService reviewQueryService) {
        this.reviewQueryService = reviewQueryService;
    }

    @GetMapping("/detail/{storeId}/review")
    public ResponseEntity<?> getStoreReviews(
            @PathVariable Integer storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ReviewSimpleDto> result = reviewQueryService.getStoreReviews(storeId, page, size);
        return ResponseEntity.ok(
                Map.of(
                        "items", result.getContent(),
                        "total", result.getTotalElements(),
                        "page", result.getNumber(),
                        "size", result.getSize()
                )
        );
    }
}
