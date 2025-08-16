// src/main/java/org/example/schoolallianceinfor/dto/PartnershipInfoDto.java
package org.example.schoolallianceinfor.dto.partnership;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnershipInfoDto {
    private Integer partnershipId;
    private String content;
    private String storeName;
    private String organization;
    private String category;
    private String type;
}
