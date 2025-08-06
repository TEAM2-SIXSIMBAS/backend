package org.example.schoolallianceinfor.dto;

import org.example.schoolallianceinfor.entity.Partnership;
import org.example.schoolallianceinfor.entity.Review;

import java.util.List;

public class ReviewDto {
    private Integer reviewId;
    private String receiptUrl;
    private List<String> photoUrl;
    private String text;
    private Integer partnershipId;

    public ReviewDto() {}

    // ✅ 엔티티 → DTO 변환용 생성자 (응답 시 사용)
    public ReviewDto(Review review) {
        this.reviewId = review.getReviewId();
        this.receiptUrl = review.getReceiptUrl();
        this.photoUrl = review.getPhotoUrl();
        this.text = review.getText();
        this.partnershipId = review.getPartnership() != null ? review.getPartnership().getPartnershipId() : null;
    }

    // ✅ DTO → 엔티티 변환 (요청 시 사용)
    public Review toEntity(Partnership partnership) {
        Review review = new Review();
        review.setReceiptUrl(this.receiptUrl);
        review.setPhotoUrl(this.photoUrl);
        review.setText(this.text);
        review.setPartnership(partnership);
        return review;
    }

    // Getter & Setter
    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public List<String> getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(List<String> photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPartnershipId() {
        return partnershipId;
    }

    public void setPartnershipId(Integer partnershipId) {
        this.partnershipId = partnershipId;
    }
}