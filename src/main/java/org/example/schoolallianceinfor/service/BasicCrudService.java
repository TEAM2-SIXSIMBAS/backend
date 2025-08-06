package org.example.schoolallianceinfor.service;

import org.example.schoolallianceinfor.dto.PartnershipDto;
import org.example.schoolallianceinfor.dto.ReviewDto;
import org.example.schoolallianceinfor.dto.StoreDto;
import org.example.schoolallianceinfor.entity.Partnership;
import org.example.schoolallianceinfor.entity.Review;
import org.example.schoolallianceinfor.entity.Store;
import org.example.schoolallianceinfor.repository.PartnershipRepository;
import org.example.schoolallianceinfor.repository.ReviewRepository;
import org.example.schoolallianceinfor.repository.StoreRepository;
import org.springframework.stereotype.Service;

@Service
public class BasicCrudService {

    private final StoreRepository storeRepository;
    private final PartnershipRepository partnershipRepository;
    private final ReviewRepository reviewRepository;

    public BasicCrudService(StoreRepository storeRepository, PartnershipRepository partnershipRepository, ReviewRepository reviewRepository) {
        this.storeRepository = storeRepository;
        this.partnershipRepository = partnershipRepository;
        this.reviewRepository = reviewRepository;
    }

    // Store 생성
    public Integer createStore(StoreDto dto) {
        Store store = dto.toEntity(); // DTO에 toEntity()가 있다고 가정
        storeRepository.save(store);
        return store.getStoreId();
    }

    // Partnership 생성
    public Integer createPartnership(PartnershipDto dto) {
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));

        Partnership partnership = dto.toEntity(store);
        partnershipRepository.save(partnership);
        return partnership.getPartnershipId();
    }

    // Review 생성
    public Long createReview(ReviewDto dto) {
        Partnership partnership = partnershipRepository.findById(dto.getPartnershipId())
                .orElseThrow(() -> new IllegalArgumentException("Partnership not found"));

        Review review = dto.toEntity(partnership);
        reviewRepository.save(review);
        return review.getReviewId().longValue();
    }

    // Store 조회
    public StoreDto readStoreById(Integer id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));
        return new StoreDto(store);
    }

    // Partnership 조회
    public PartnershipDto readPartnershipById(Integer id) {
        Partnership p = partnershipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Partnership not found"));
        return new PartnershipDto(p);
    }

    // Review 조회
    public ReviewDto readReviewById(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        return new ReviewDto(review);
    }

    // Store 삭제
    public void deleteStore(Integer id) {
        storeRepository.deleteById(id);
    }

    // Partnership 삭제
    public void deletePartnership(Integer id) {
        partnershipRepository.deleteById(id);
    }

    // Review 삭제
    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }

}
