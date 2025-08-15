// src/main/java/org/example/schoolallianceinfor/service/BasicCrudService.java
package org.example.schoolallianceinfor.service;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.store.StoreRequest;
import org.example.schoolallianceinfor.dto.store.StoreResponse;
import org.example.schoolallianceinfor.dto.partnership.PartnershipRequest;
import org.example.schoolallianceinfor.dto.partnership.PartnershipResponse;
import org.example.schoolallianceinfor.dto.review.ReviewRequest;
import org.example.schoolallianceinfor.dto.review.ReviewResponse;
import org.example.schoolallianceinfor.entity.Partnership;
import org.example.schoolallianceinfor.entity.Review;
import org.example.schoolallianceinfor.entity.Store;
import org.example.schoolallianceinfor.mapper.PartnershipMapper;
import org.example.schoolallianceinfor.mapper.ReviewMapper;
import org.example.schoolallianceinfor.mapper.StoreMapper;
import org.example.schoolallianceinfor.repository.PartnershipRepository;
import org.example.schoolallianceinfor.repository.ReviewRepository;
import org.example.schoolallianceinfor.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasicCrudService {

    private final StoreRepository storeRepository;
    private final PartnershipRepository partnershipRepository;
    private final ReviewRepository reviewRepository;

    private final StoreMapper storeMapper;
    private final PartnershipMapper partnershipMapper;
    private final ReviewMapper reviewMapper;

    /* ===================== Store ===================== */

    @Transactional
    public StoreResponse createStore(StoreRequest req) {
        Store entity = storeMapper.toEntity(req);
        Store saved = storeRepository.save(entity);
        return storeMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public StoreResponse readStoreById(Integer id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Store not found: " + id));
        return storeMapper.toResponse(store);
    }

    @Transactional
    public void deleteStore(Integer id) {
        storeRepository.deleteById(id);
    }

    /* ================== Partnership ================== */

    @Transactional
    public PartnershipResponse createPartnership(PartnershipRequest req) {
        Store store = storeRepository.findById(req.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Store not found: " + req.getStoreId()));
        Partnership entity = partnershipMapper.toEntity(req, store);
        Partnership saved = partnershipRepository.save(entity);
        return partnershipMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public PartnershipResponse readPartnershipById(Integer id) {
        Partnership p = partnershipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Partnership not found: " + id));
        return partnershipMapper.toResponse(p);
    }

    @Transactional
    public void deletePartnership(Integer id) {
        partnershipRepository.deleteById(id);
    }

    /* ===================== Review ==================== */

    @Transactional
    public ReviewResponse createReview(ReviewRequest req) {
        Partnership partnership = partnershipRepository.findById(req.getPartnershipId())
                .orElseThrow(() -> new IllegalArgumentException("Partnership not found: " + req.getPartnershipId()));
        Review entity = reviewMapper.toEntity(req, partnership);
        Review saved = reviewRepository.save(entity);
        return reviewMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ReviewResponse readReviewById(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found: " + id));
        return reviewMapper.toResponse(review);
    }

    @Transactional
    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }
}
