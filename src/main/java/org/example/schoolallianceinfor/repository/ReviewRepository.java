package org.example.schoolallianceinfor.repository;

import org.example.schoolallianceinfor.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // 기존: storeId 기준 페이징 조회 (그대로 유지)
    @Query("""
        select r
        from Review r
        where r.partnership.store.storeId = :storeId
    """)
    Page<Review> findByStoreId(@Param("storeId") Integer storeId, Pageable pageable);

    // 추가: partnershipId 기준 최신순 리스트 반환
    List<Review> findAllByPartnership_PartnershipIdOrderByReviewIdDesc(Integer partnershipId);
}
