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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basic")
@RequiredArgsConstructor
public class    BasicCrudController {

    private final BasicCrudService crud;

    // ========== Store ==========
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

    // ========== Partnership ==========
    @PostMapping("/partnerships")
    public ResponseEntity<PartnershipResponse> createPartnership(@RequestBody @Valid PartnershipRequest request) {
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

    // ========== Review ==========
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
