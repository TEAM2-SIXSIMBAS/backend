package org.example.schoolallianceinfor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnershipRequestDto {
    private String storeName;
    private String content;
    private String target;
    private String type;
    private Integer discountRate;
    private String saleStartDate;
    private String saleEndDate;
    private String useStartDate;
    private String useEndDate;
    private String note;
}
