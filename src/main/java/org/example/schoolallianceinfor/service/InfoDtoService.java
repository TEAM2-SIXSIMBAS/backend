package org.example.schoolallianceinfor.service;

import lombok.RequiredArgsConstructor;
import org.example.schoolallianceinfor.dto.StoreInfoDto;
import org.example.schoolallianceinfor.dto.PartnershipInfoDto;
import org.example.schoolallianceinfor.repository.StoreRepository;
import org.example.schoolallianceinfor.repository.PartnershipRepository;
import org.example.schoolallianceinfor.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfoDtoService {

    private final StoreRepository storeRepository;
    private final PartnershipRepository partnershipRepository;
    private final ReviewRepository reviewRepository;

    // ===== Store Info 조회 =====
    public List<StoreInfoDto> getAllStoreInfo() {
        return storeRepository.findAll().stream()
                .map(StoreInfoDto::new)
                .collect(Collectors.toList());
    }

    // ===== Partnership Info 조회 =====
    public List<PartnershipInfoDto> getAllPartnershipInfo() {
        return partnershipRepository.findAll().stream()
                .map(PartnershipInfoDto::new)
                .collect(Collectors.toList());
    }
}
