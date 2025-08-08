package org.example.schoolallianceinfor.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PartnershipResponseDto {
    private Integer partnershipId;
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
    private Integer views;
}
