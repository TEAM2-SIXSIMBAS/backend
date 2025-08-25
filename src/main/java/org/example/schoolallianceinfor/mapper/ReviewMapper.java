package org.example.schoolallianceinfor.mapper;

import org.example.schoolallianceinfor.dto.review.ReviewRequest;
import org.example.schoolallianceinfor.dto.review.ReviewResponse;
import org.example.schoolallianceinfor.entity.Partnership;
import org.example.schoolallianceinfor.entity.Review;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ReviewMapper {

    public Review toEntity(ReviewRequest r, Partnership partnership) {
        if (r == null) return null;
        return Review.builder()
                .receiptUrl(r.getReceiptUrl())
                .photoUrl(r.getPhotoUrl())
                .text(r.getText())
                .partnership(partnership)
                .build();
    }

    public ReviewResponse toResponse(Review e) {
        if (e == null) return null;
        return ReviewResponse.builder()
                .reviewId(e.getReviewId())
                .receiptUrl(e.getReceiptUrl())
                .photoUrl(e.getPhotoUrl())
                .text(e.getText())
                .partnershipId(e.getPartnership() != null ? e.getPartnership().getPartnershipId() : null)
                .build();
    }

    public List<ReviewResponse> toResponseList(List<Review> entities) {
        if (entities == null) return List.of();
        return entities.stream().filter(Objects::nonNull).map(this::toResponse).collect(Collectors.toList());
    }
}
