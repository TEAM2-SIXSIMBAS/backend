package org.example.schoolallianceinfor.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    private Integer partnershipId;

    private String receiptUrl;

    @ElementCollection
    @CollectionTable(name = "review_photos", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "photo_url")
    private List<String> photoUrl=new ArrayList<>();

    private String text;

    public Integer getReviewId() { return reviewId; }
    public Integer getPartnershipId() { return partnershipId; }
    public String getReceiptUrl() { return receiptUrl; }
    public List<String> getPhotoUrl() { return photoUrl; }
    public String getText() { return text; }

    public void setPartnershipId(Integer partnershipId) { this.partnershipId = partnershipId; }
    public void setReceiptUrl(String receiptUrl) { this.receiptUrl = receiptUrl; }
    public void setPhotoUrl(List<String> photoUrl) { this.photoUrl = photoUrl; }
    public void setText(String text) { this.text = text; }
}
