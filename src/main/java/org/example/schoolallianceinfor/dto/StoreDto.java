package org.example.schoolallianceinfor.dto;

import org.example.schoolallianceinfor.entity.Store;

import java.time.LocalTime;

public class StoreDto {

    private Integer storeId; // 응답 시 사용됨
    private String storeName;
    private Integer phoneNumber;
    private String email;
    private String address;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String organization;
    private String category;

    // ✅ 기본 생성자 (역직렬화용)
    public StoreDto() {}

    // ✅ 엔티티 → DTO 변환용 생성자 (응답 시 사용)
    public StoreDto(Store store) {
        this.storeId = store.getStoreId();
        this.storeName = store.getStoreName();
        this.phoneNumber = store.getPhoneNumber();
        this.email = store.getEmail();
        this.address = store.getAddress();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.organization = store.getOrganization();
        this.category = store.getCategory();
    }

    // ✅ DTO → 엔티티 변환 (요청 시 사용)
    public Store toEntity() {
        return new Store(
                storeName,
                phoneNumber,
                email,
                address,
                openTime,
                closeTime,
                organization,
                category
        );
    }

    // ✅ getter / setter (필요 시 Lombok @Getter/@Setter 사용 가능)
    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}