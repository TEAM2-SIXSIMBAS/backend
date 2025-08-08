package org.example.schoolallianceinfor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnershipSearchCondition {
    private String category;      // 제휴 종류 (ex. 할인, 서비스)
    private String organization;  // 담당 기관
    private String type;
    private String target;// 업종 등 (ex. 음식, 카페 등)
    private String sortBy;        // 정렬 조건 (discountRate, views, saleStartDate 등)
}
