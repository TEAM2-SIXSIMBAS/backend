package org.example.schoolallianceinfor.service;

import org.example.schoolallianceinfor.dto.PartnershipInfoDto;
import org.example.schoolallianceinfor.entity.Partnership;
import org.example.schoolallianceinfor.repository.PartnershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartnershipService {
    private final PartnershipRepository partnershipRepository;

    public PartnershipService(PartnershipRepository partnershipRepository) {
        this.partnershipRepository = partnershipRepository;
    }

    @Transactional(readOnly=true)
    public PartnershipInfoDto getPartnershipInfo(Integer id) {
        Partnership p = partnershipRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 제휴 정보 입니다. id="+id));

        return new PartnershipInfoDto(
                p.getContent(),
                p.getStore().getStoreName(),
                p.getStore().getOrganization(),
                p.getStore().getCategory(),
                p.getType()
        );
    }
}
