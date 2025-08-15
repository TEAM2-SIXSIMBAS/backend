package org.example.schoolallianceinfor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;

    @Column(name = "storeName", length = 500)
    private String storeName;

    @Column(name = "phoneNumber")   // 숫자는 length 불필요
    private Integer phoneNumber;

    @Column(name = "email", length = 500)
    private String email;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "openTime")      // 시간/날짜는 length 불필요
    private LocalTime openTime;

    @Column(name = "closeTime")
    private LocalTime closeTime;

    @Column(name = "organization", length = 500)
    private String organization;

    @Column(name = "category", length = 500)
    private String category;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Partnership> partnerships = new ArrayList<>();
}
