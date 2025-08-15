package org.example.schoolallianceinfor.dto.partnership;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PartnershipResponse {
    private Integer partnershipId;
    private String content;
    private String target;
    private String type;
    private Integer discountRate;
    private LocalDate saleStartDate;
    private LocalDate saleEndDate;
    private LocalDate useStartDate;
    private LocalDate useEndDate;
    private String note;
    private Integer views;   // 서버 계산/관리 값
    private Integer storeId; // 연관 ID만 포함
}
