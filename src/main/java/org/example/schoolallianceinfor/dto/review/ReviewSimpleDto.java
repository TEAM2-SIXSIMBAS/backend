package org.example.schoolallianceinfor.dto.review;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewSimpleDto {
    private List<String> photoUrl;
    private String text;
}
