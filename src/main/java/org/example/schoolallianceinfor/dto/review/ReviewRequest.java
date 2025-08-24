package org.example.schoolallianceinfor.dto.review;

import lombok.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewRequest {
    @NotNull  private Integer partnershipId;
    @NotBlank private String receiptUrl;
    private List<String> photoUrl; // 없으면 빈 리스트로 처리
    private String text;
}
