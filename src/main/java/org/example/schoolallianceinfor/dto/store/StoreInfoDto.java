// src/main/java/org/example/schoolallianceinfor/dto/StoreInfoDto.java
package org.example.schoolallianceinfor.dto.store;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreInfoDto {
    private Integer storeId;
    private String storeName;
    private Integer phoneNumber;
    private String email;
    private String address;
    private LocalTime startTime;
    private LocalTime endTime;
    private String parkingAvailable;
}
