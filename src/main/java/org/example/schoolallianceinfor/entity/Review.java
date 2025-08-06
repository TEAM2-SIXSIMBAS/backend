package org.example.schoolallianceinfor.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;
    @Column(name = "receiptUrl", length = 500)
    private String receiptUrl;
    @Column(name = "photoUrl", length = 500)
    private List<String> photoUrl;
    @Column(name = "text", length = 500)
    private String text;

    @ManyToOne
    @JoinColumn(name = "partnership_id")  // Review 테이블에 partnership_id FK 생성
    private Partnership partnership;

    public Review(String receiptUrl, List<String> photoUrl, String text, String note, Integer views) {
        this.receiptUrl = receiptUrl;
        this.photoUrl = photoUrl;
        this.text = text;
    }

    public Review() {}

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

    public Partnership getPartnership() {
        return partnership;
    }

    public void setPartnership(Partnership partnership) {
        this.partnership = partnership;
    }
}
