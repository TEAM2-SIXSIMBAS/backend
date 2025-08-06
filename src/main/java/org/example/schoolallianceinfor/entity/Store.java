package org.example.schoolallianceinfor.entity;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;
    @Column(name = "storeName", length = 500)
    private String storeName;
    @Column(name = "phoneNumber", length = 500)
    private Integer phoneNumber;
    @Column(name = "email", length = 500)
    private String email;
    @Column(name = "address", length = 500)
    private String address;
    @Column(name = "openTime", length = 500)
    private LocalTime openTime;
    @Column(name = "closeTime", length = 500)
    private LocalTime closeTime;
    @Column(name = "organization", length = 500)
    private String organization;
    @Column(name = "category", length = 500)
    private String category;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Partnership> partnerships = new ArrayList<>();

    public Store(String storeName, Integer phoneNumber, String email, String address, LocalTime openTime, LocalTime closeTime, String organization, String category) {
        this.storeName = storeName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.address = address;
        this.organization = organization;
        this.category = category;
    }

    public Store() {

    }

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

    public List<Partnership> getPartnerships() {
        return partnerships;
    }

    public void setPartnerships(List<Partnership> partnerships) {
        this.partnerships = partnerships;
    }
}


