package org.example.schoolallianceinfor.dto.store;

import lombok.*;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StoreResponse {
    private Integer storeId;
    private String storeName;
    private Integer phoneNumber; // (가능하면 String 권장)
    private String email;
    private String address;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String organization;
    private String category;
    private String parkingAvailable;
}
