package org.example.schoolallianceinfor.controller;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.StoreInfoDto;
import org.example.schoolallianceinfor.dto.PartnershipInfoDto;
import org.example.schoolallianceinfor.service.InfoDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/infos")
@RequiredArgsConstructor
public class InfoDtoController {

    private final InfoDtoService infoService;

    // ====== StoreInfoDto 조회 ======
    @GetMapping("/StoreInfoDto")
    public ResponseEntity<List<StoreInfoDto>> getAllStores() {
        return ResponseEntity.ok(infoService.getAllStoreInfo());
    }

    // ====== PartnershipInfoDto 조회 ======
    @GetMapping("/PartnershipInfoDto")
    public ResponseEntity<List<PartnershipInfoDto>> getAllPartnerships() {
        return ResponseEntity.ok(infoService.getAllPartnershipInfo());
    }
}
