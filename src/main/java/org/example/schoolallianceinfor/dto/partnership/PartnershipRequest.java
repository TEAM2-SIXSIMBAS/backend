package org.example.schoolallianceinfor.dto.partnership;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PartnershipRequest {
    @NotNull  private Integer storeId;     // 연관관계는 ID로만 받기
    @NotBlank private String content;
    @NotBlank private String target;
    @NotBlank private String type;
    @NotNull  private Integer discountRate;
    private LocalDate saleStartDate;
    private LocalDate saleEndDate;
    private LocalDate useStartDate;
    private LocalDate useEndDate;
    private String note;

    // views는 서버가 관리 → 요청에서 제외(생성 시 0으로 기본값 처리 권장)
}
