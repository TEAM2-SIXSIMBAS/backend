package org.example.schoolallianceinfor.dto.review;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewResponse {
    private Integer reviewId;
    private String receiptUrl;
    private List<String> photoUrl;
    private String text;
    private Integer partnershipId;
}
