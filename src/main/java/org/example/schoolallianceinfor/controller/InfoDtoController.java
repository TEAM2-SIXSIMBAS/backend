// src/main/java/org/example/schoolallianceinfor/controller/InfoDtoController.java
package org.example.schoolallianceinfor.controller;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.store.StoreInfoDto;
import org.example.schoolallianceinfor.dto.partnership.PartnershipInfoDto;
import org.example.schoolallianceinfor.service.InfoDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/infos")
@RequiredArgsConstructor
public class InfoDtoController {

    private final InfoDtoService infoService;

    /** ===== StoreInfoDto 전체 조회 ===== */
    @GetMapping("/stores")
    public ResponseEntity<List<StoreInfoDto>> getAllStores() {
        return ResponseEntity.ok(infoService.getAllStoreInfo());
    }

    /** ===== PartnershipInfoDto 전체 조회 ===== */
    @GetMapping("/partnerships")
    public ResponseEntity<List<PartnershipInfoDto>> getAllPartnerships() {
        return ResponseEntity.ok(infoService.getAllPartnershipInfo());
    }
}
