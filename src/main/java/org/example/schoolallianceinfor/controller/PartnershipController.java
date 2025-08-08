package org.example.schoolallianceinfor.controller;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.domain.Partnership;
import org.example.schoolallianceinfor.dto.PartnershipRequestDto;
import org.example.schoolallianceinfor.dto.PartnershipResponseDto;
import org.example.schoolallianceinfor.dto.PartnershipSearchCondition;
import org.example.schoolallianceinfor.service.PartnershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/partnerships")
public class PartnershipController {

    private final PartnershipService partnershipService;

    // ✅ 제휴 정보 등록
    @PostMapping
    public ResponseEntity<PartnershipResponseDto> createPartnership(@RequestBody PartnershipRequestDto requestDto) {
        Partnership saved = partnershipService.save(requestDto);
        PartnershipResponseDto responseDto = convertToDto(saved);
        return ResponseEntity.ok(responseDto);
    }

    // ✅ 제휴 정보 전체 조회 + 필터 + 정렬
    @GetMapping
    public ResponseEntity<List<PartnershipResponseDto>> getAllPartnerships(@ModelAttribute PartnershipSearchCondition condition) {
        List<Partnership> partnerships = partnershipService.search(condition);
        List<PartnershipResponseDto> result = partnerships.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // ✅ 단일 제휴 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<PartnershipResponseDto> getPartnershipById(@PathVariable Integer id) {
        Partnership p = partnershipService.findById(id);
        return ResponseEntity.ok(convertToDto(p));
    }

    // ✅ Entity → DTO 변환 메서드
    private PartnershipResponseDto convertToDto(Partnership p) {
        return PartnershipResponseDto.builder()
                .partnershipId(p.getPartnershipId())
                .storeName(p.getStoreName())
                .content(p.getContent())
                .target(p.getTarget())
                .type(p.getType())
                .discountRate(p.getDiscountRate())
                .saleStartDate(p.getSaleStartDate().toString())
                .saleEndDate(p.getSaleEndDate().toString())
                .useStartDate(p.getUseStartDate().toString())
                .useEndDate(p.getUseEndDate().toString())
                .note(p.getNote())
                .views(p.getViews())
                .build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<PartnershipResponseDto>> searchPartnerships(@ModelAttribute PartnershipSearchCondition condition) {
        List<Partnership> partnerships = partnershipService.search(condition);
        List<PartnershipResponseDto> result = partnerships.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
