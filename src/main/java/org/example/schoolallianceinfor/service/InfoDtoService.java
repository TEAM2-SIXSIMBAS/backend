// src/main/java/org/example/schoolallianceinfor/service/InfoDtoService.java
package org.example.schoolallianceinfor.service;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.store.StoreInfoDto;
import org.example.schoolallianceinfor.dto.partnership.PartnershipInfoDto;
import org.example.schoolallianceinfor.mapper.StoreMapper;
import org.example.schoolallianceinfor.mapper.PartnershipMapper;
import org.example.schoolallianceinfor.repository.StoreRepository;
import org.example.schoolallianceinfor.repository.PartnershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InfoDtoService {

    private final StoreRepository storeRepository;
    private final PartnershipRepository partnershipRepository;

    private final StoreMapper storeMapper;
    private final PartnershipMapper partnershipMapper;

    /** ===== Store Info 조회 ===== */
    public List<StoreInfoDto> getAllStoreInfo() {
        return storeRepository.findAll()
                .stream()
                .map(storeMapper::toInfo)      // 엔티티 -> StoreInfoDto
                .toList();
    }

    /** ===== Partnership Info 조회 ===== */
    public List<PartnershipInfoDto> getAllPartnershipInfo() {
        return partnershipRepository.findAll()
                .stream()
                .map(partnershipMapper::toInfo) // 엔티티 -> PartnershipInfoDto (신규 스펙)
                .toList();
    }
}
