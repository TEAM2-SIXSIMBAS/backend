package org.example.schoolallianceinfor.dto;

import org.example.schoolallianceinfor.entity.Partnership;
import org.example.schoolallianceinfor.entity.Store;

import java.time.LocalDate;

public class PartnershipDto {


    private Integer partnershipId;
    private String content;
    private String target;
    private String type;
    private Integer discountRate;
    private LocalDate saleStartDate;
    private LocalDate saleEndDate;
    private LocalDate useStartDate;
    private LocalDate useEndDate;
    private String note;
    private Integer views;

    private Integer storeId; // 연관된 Store ID만 담음

    // ✅ 기본 생성자
    public PartnershipDto() {}

    // ✅ Entity → DTO
    public PartnershipDto(Partnership p) {
        this.partnershipId = p.getPartnershipId();
        this.content = p.getContent();
        this.target = p.getTarget();
        this.type = p.getType();
        this.discountRate = p.getDiscountRate();
        this.saleStartDate = p.getSaleStartDate();
        this.saleEndDate = p.getSaleEndDate();
        this.useStartDate = p.getUseStartDate();
        this.useEndDate = p.getUseEndDate();
        this.note = p.getNote();
        this.views = p.getViews();
        this.storeId = p.getStore() != null ? p.getStore().getStoreId() : null;
    }

    // ✅ DTO → Entity
    public Partnership toEntity(Store store) {
        Partnership p = new Partnership();
        p.setContent(this.content);
        p.setTarget(this.target);
        p.setType(this.type);
        p.setDiscountRate(this.discountRate);
        p.setSaleStartDate(this.saleStartDate);
        p.setSaleEndDate(this.saleEndDate);
        p.setUseStartDate(this.useStartDate);
        p.setUseEndDate(this.useEndDate);
        p.setNote(this.note);
        p.setViews(this.views);
        p.setStore(store); // 연관된 Store 주입
        return p;
    }

    // ✅ Getter/Setter 생략 가능, 필요 시 Lombok 사용 권장
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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
