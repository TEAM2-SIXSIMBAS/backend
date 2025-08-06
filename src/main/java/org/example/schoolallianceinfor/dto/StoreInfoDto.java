package org.example.schoolallianceinfor.dto;

import org.example.schoolallianceinfor.entity.Store;

public class StoreInfoDto {
    private String storeName;
    private Integer phoneNumber;

    public StoreInfoDto(Store store) {
        this.storeName = store.getStoreName();
        this.phoneNumber = store.getPhoneNumber();
    }

    public StoreInfoDto() {} // storeName: null, phoneNumber: null 이런 비었지만 속성 정보는 가지고 있는 것을 만드는 기본 생성자

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
}
