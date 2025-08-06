package org.example.schoolallianceinfor.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Partnership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer partnershipId;
    @Column(name = "content", length = 500)
    private String content;
    @Column(name = "target", length = 500)
    private String target;
    @Column(name = "type", length = 500)
    private String type;
    @Column(name = "discountRate", length = 500)
    private Integer discountRate;
    @Column(name = "saleStartDate", length = 500)
    private LocalDate saleStartDate;
    @Column(name = "saleEndDate", length = 500)
    private LocalDate saleEndDate;
    @Column(name = "useStartDate", length = 500)
    private LocalDate useStartDate;
    @Column(name = "useEndDate", length = 500)
    private LocalDate useEndDate;
    @Column(name = "note", length = 500)
    private String note;
    @Column(name = "views", length = 500)
    private Integer views;

    @ManyToOne
    @JoinColumn(name = "store_id")  // Partnership 테이블에 store_id FK 생성
    private Store store;

    @OneToMany(mappedBy = "partnership", cascade = CascadeType.ALL)
    private List<Review> Reviews = new ArrayList<>();

    public Partnership(String content, String target, String type, Integer discountRate, LocalDate saleStartDate, LocalDate saleEndDate, LocalDate useStartDate, LocalDate useEndDate, String note, Integer views) {
        this.content = content;
        this.target = target;
        this.type = type;
        this.discountRate = discountRate;
        this.saleStartDate = saleStartDate;
        this.saleEndDate = saleEndDate;
        this.useStartDate = useStartDate;
        this.useEndDate = useEndDate;
        this.note = note;
        this.views = views;
    }

    public Partnership() {

    }

    public Integer getPartnershipId() {
        return partnershipId;
    }

    public void setPartnershipId(Integer partnershipId) {
        this.partnershipId = partnershipId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Integer discountRate) {
        this.discountRate = discountRate;
    }

    public LocalDate getSaleStartDate() {
        return saleStartDate;
    }

    public void setSaleStartDate(LocalDate saleStartDate) {
        this.saleStartDate = saleStartDate;
    }

    public LocalDate getSaleEndDate() {
        return saleEndDate;
    }

    public void setSaleEndDate(LocalDate saleEndDate) {
        this.saleEndDate = saleEndDate;
    }

    public LocalDate getUseStartDate() {
        return useStartDate;
    }

    public void setUseStartDate(LocalDate useStartDate) {
        this.useStartDate = useStartDate;
    }

    public LocalDate getUseEndDate() {
        return useEndDate;
    }

    public void setUseEndDate(LocalDate useEndDate) {
        this.useEndDate = useEndDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Review> getReviews() {
        return Reviews;
    }

    public void setReviews(List<Review> reviews) {
        Reviews = reviews;
    }
}

