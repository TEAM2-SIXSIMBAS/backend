package org.example.schoolallianceinfor.controller;

import org.example.schoolallianceinfor.dto.PartnershipInfoDto;
import org.example.schoolallianceinfor.service.PartnershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/partnerships")
public class PartnershipController {
    private final PartnershipService partnershipService;

    public PartnershipController(PartnershipService partnershipService) {
        this.partnershipService = partnershipService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnershipInfoDto> getPartnershipInfo(@PathVariable Integer id) {
        PartnershipInfoDto dto = partnershipService.getPartnershipInfo(id);
        return ResponseEntity.ok(dto);
    }
}
