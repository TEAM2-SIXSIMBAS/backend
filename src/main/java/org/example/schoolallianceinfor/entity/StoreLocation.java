package org.example.schoolallianceinfor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "store_location", indexes = {
        @Index(name = "idx_store_location_lat_lng", columnList = "lat,lng")
})
public class StoreLocation {

    // Store와 PK 공유 → 진짜 1:1
    @Id
    private Integer storeId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    // 좌표 (카카오: x=lng, y=lat → 저장은 (lat, lng))
    private Double lat;
    private Double lng;

    // 메타(선택): 마지막 지오코딩 시각/메모
    private LocalDateTime geoUpdatedAt;

    @Column(length = 300)
    private String geoNote;
}
