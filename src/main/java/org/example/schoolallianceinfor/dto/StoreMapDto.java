package org.example.schoolallianceinfor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreMapDto {
    private String storeName;
    private String address;
    private Double latitude;
    private Double longitude;
    private String category;
    private String organization;
    private String type;
    private Integer discountRate;
}
