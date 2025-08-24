// src/main/java/org/example/schoolallianceinfor/dto/partnership/PartnershipRequest.java
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
    @Min(0)
    private Integer views;

    /** ✅ 컨트롤러에서 이미지 업로드 후 주입해줄 URL (없으면 null) */
    private String partnershipImageUrl;
}
