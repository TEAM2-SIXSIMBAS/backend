package org.example.schoolallianceinfor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreMarkerDto {
    private String storeName;
    private double latitude;
    private double longitude;
}
