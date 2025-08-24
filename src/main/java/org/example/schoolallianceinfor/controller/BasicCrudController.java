// src/main/java/org/example/schoolallianceinfor/controller/BasicCrudController.java
package org.example.schoolallianceinfor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.partnership.PartnershipRequest;
import org.example.schoolallianceinfor.dto.partnership.PartnershipResponse;
import org.example.schoolallianceinfor.dto.review.ReviewRequest;
import org.example.schoolallianceinfor.dto.review.ReviewResponse;
import org.example.schoolallianceinfor.dto.store.StoreRequest;
import org.example.schoolallianceinfor.dto.store.StoreResponse;
import org.example.schoolallianceinfor.service.BasicCrudService;
import org.example.schoolallianceinfor.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/basic")
@RequiredArgsConstructor
public class BasicCrudController {

    private final BasicCrudService crud;
    private final FileStorageService fileStorageService;

    /* ===================== Store ===================== */

    @PostMapping("/stores")
    public ResponseEntity<StoreResponse> createStore(@RequestBody @Valid StoreRequest request) {
        StoreResponse resp = crud.createStore(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity<StoreResponse> getStore(@PathVariable Integer id) {
        return ResponseEntity.ok(crud.readStoreById(id));
    }

    @DeleteMapping("/stores/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Integer id) {
        crud.deleteStore(id);
        return ResponseEntity.noContent().build();
    }

    /* ================== Partnership ================== */

    // (A) JSON 전용: 클라이언트가 partnershipImageUrl을 직접 JSON으로 줄 때 사용
    @PostMapping(
            value = "/partnerships",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PartnershipResponse> createPartnershipJson(
            @RequestBody @Valid PartnershipRequest request
    ) {
        // 요청 JSON에 partnershipImageUrl 이 포함되어 있으면 그대로 저장, 아니면 null 저장
        PartnershipResponse resp = crud.createPartnership(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // (B) 멀티파트 전용: 이미지 파일을 함께 업로드하는 경우
    @PostMapping(
            value = "/partnerships",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PartnershipResponse> createPartnershipMultipart(
            @RequestPart("data") @Valid PartnershipRequest request,                // JSON DTO
            @RequestPart(value = "image", required = false) MultipartFile image   // ✅ 선택
    ) throws IOException {

        // 이미지가 있으면 저장 → URL 생성 → DTO에 주입 (리뷰 방식과 동일)
        if (image != null && !image.isEmpty()) {
            String url = fileStorageService.saveOne(image);
            request.setPartnershipImageUrl(url);
        } else {
            request.setPartnershipImageUrl(null); // 이미지 없으면 null 저장
        }

        PartnershipResponse resp = crud.createPartnership(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/partnerships/{id}")
    public ResponseEntity<PartnershipResponse> getPartnership(@PathVariable Integer id) {
        return ResponseEntity.ok(crud.readPartnershipById(id));
    }

    @DeleteMapping("/partnerships/{id}")
    public ResponseEntity<Void> deletePartnership(@PathVariable Integer id) {
        crud.deletePartnership(id);
        return ResponseEntity.noContent().build();
    }

    /* ===================== Review ==================== */

    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponse> createReview(@RequestBody @Valid ReviewRequest request) {
        ReviewResponse resp = crud.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Integer id) {
        return ResponseEntity.ok(crud.readReviewById(id));
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        crud.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
