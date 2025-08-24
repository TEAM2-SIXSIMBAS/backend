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
import org.example.schoolallianceinfor.entity.StoreLocation;
import org.example.schoolallianceinfor.mapper.PartnershipMapper;
import org.example.schoolallianceinfor.mapper.ReviewMapper;
import org.example.schoolallianceinfor.mapper.StoreMapper;
import org.example.schoolallianceinfor.repository.PartnershipRepository;
import org.example.schoolallianceinfor.repository.ReviewRepository;
import org.example.schoolallianceinfor.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BasicCrudService {

    private final StoreRepository storeRepository;
    private final PartnershipRepository partnershipRepository;
    private final ReviewRepository reviewRepository;
    private final KakaoMapService KakaoMapService; // 기존 네이밍 유지
    private final StoreMapper storeMapper;
    private final PartnershipMapper partnershipMapper;
    private final ReviewMapper reviewMapper;

    /* ===================== Store ===================== */

    @Transactional
    public StoreResponse createStore(StoreRequest req) {
        // 1) Store 저장
        Store store = storeMapper.toEntity(req);
        storeRepository.save(store);

        // 2) 좌표 resolve (주소 우선 → 이름 폴백)
        double[] coord = KakaoMapService.resolveCoordinate(store.getAddress(), store.getStoreName());

        if (coord[0] != 0.0 || coord[1] != 0.0) {
            StoreLocation location = StoreLocation.builder()
                    .store(store)
                    .lat(coord[0])
                    .lng(coord[1])
                    .geoUpdatedAt(LocalDateTime.now())
                    .geoNote("geocoded by: " + (store.getAddress() != null && !store.getAddress().isBlank()
                            ? "address" : "name"))
                    .build();
            store.setLocation(location); // cascade=ALL 가정
        } else {
            StoreLocation location = StoreLocation.builder()
                    .store(store)
                    .lat(null)
                    .lng(null)
                    .geoUpdatedAt(LocalDateTime.now())
                    .geoNote("geocode failed")
                    .build();
            store.setLocation(location);
        }
        return storeMapper.toResponse(store);
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

    // 컨트롤러에서 image 업로드 → request.setPartnershipImageUrl(url) 주입 후 호출
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
