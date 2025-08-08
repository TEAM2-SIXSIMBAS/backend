package org.example.schoolallianceinfor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoordinateDto {
    private double latitude;   // 위도
    private double longitude;  // 경도
}
