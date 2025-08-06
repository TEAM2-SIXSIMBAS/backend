package org.example.schoolallianceinfor.controller;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.PartnershipDto;
import org.example.schoolallianceinfor.dto.ReviewDto;
import org.example.schoolallianceinfor.dto.StoreDto;
import org.example.schoolallianceinfor.service.BasicCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/basic")
@RequiredArgsConstructor
public class BasicCrudController {
    private final BasicCrudService crud;

    // ========== Store ==========
    @PostMapping("/stores")
    public ResponseEntity<Void> createStore(@RequestBody StoreDto dto) {
        crud.createStore(dto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity<StoreDto> getStore(@PathVariable Integer id) {
        return ResponseEntity.ok(crud.readStoreById(id));
    }

    @DeleteMapping("/stores/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Integer id) {
        crud.deleteStore(id);
        return ResponseEntity.noContent().build();
    }

    // ========== Partnership ==========
    @PostMapping("/partnerships")
    public ResponseEntity<Void> createPartnership(@RequestBody PartnershipDto dto) {
        crud.createPartnership(dto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/partnerships/{id}")
    public ResponseEntity<PartnershipDto> getPartnership(@PathVariable Integer id) {
        return ResponseEntity.ok(crud.readPartnershipById(id));
    }

    @DeleteMapping("/partnerships/{id}")
    public ResponseEntity<Void> deletePartnership(@PathVariable Integer id) {
        crud.deletePartnership(id);
        return ResponseEntity.noContent().build();
    }

    // ========== Review ==========
    @PostMapping("/reviews")
    public ResponseEntity<Void> createReview(@RequestBody ReviewDto dto) {
        crud.createReview(dto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable Integer id) {
        return ResponseEntity.ok(crud.readReviewById(id));
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        crud.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}