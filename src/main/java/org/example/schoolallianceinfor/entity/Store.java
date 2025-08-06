package org.example.schoolallianceinfor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue
    private Integer storeId;
    private String storeName;
    private String phoneNumber;
    private String email;
    private String address;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String organization;
    private String category;
}
