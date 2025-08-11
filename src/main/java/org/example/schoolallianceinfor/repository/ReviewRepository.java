package org.example.schoolallianceinfor.repository;

import org.example.schoolallianceinfor.dto.ReviewSimpleDto;
import org.example.schoolallianceinfor.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public class ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("""
        select new org.example.schoolallianceinfor.dto.ReviewSimpleDto(r.photoUrl, r.text)
        from Review r
        join org.example.schoolallianceinfor.entity.Partnership p
             on r.partnershipId = p.partnershipId
        where p.storeId = :storeId
        order by r.reviewId desc
    """)
    Page<ReviewSimpleDto> findSimpleByStoreId(@Param("storeId") Integer storeId, Pageable pageable);
}
