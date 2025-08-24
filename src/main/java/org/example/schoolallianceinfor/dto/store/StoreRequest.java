package org.example.schoolallianceinfor.dto.store;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StoreRequest {
    @NotBlank private String storeName;
    @NotNull  private Integer phoneNumber; // (가능하면 String 권장—선택사항)
    @Email    private String email;
    @NotBlank private String address;
    @NotNull  private LocalTime openTime;
    @NotNull  private LocalTime closeTime;
    @NotBlank private String organization;
    @NotBlank private String category;
    @Pattern(regexp = "가능|불가능")
    private String parkingAvailable;

}
