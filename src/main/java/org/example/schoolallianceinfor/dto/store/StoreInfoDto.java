// src/main/java/org/example/schoolallianceinfor/dto/StoreInfoDto.java
package org.example.schoolallianceinfor.dto.store;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreInfoDto {
    private Integer storeId;
    private String storeName;
    private Integer phoneNumber;
}
