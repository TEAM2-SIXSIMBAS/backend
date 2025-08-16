// src/main/java/org/example/schoolallianceinfor/mapper/PartnershipMapper.java
package org.example.schoolallianceinfor.mapper;

import org.example.schoolallianceinfor.dto.partnership.PartnershipInfoDto;
import org.example.schoolallianceinfor.dto.partnership.PartnershipRequest;
import org.example.schoolallianceinfor.dto.partnership.PartnershipResponse;
import org.example.schoolallianceinfor.entity.Partnership;
import org.example.schoolallianceinfor.entity.Store;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PartnershipMapper {

    // === CRUD: Request -> Entity ===
    public Partnership toEntity(PartnershipRequest r, Store store) {
        if (r == null) return null;
        return Partnership.builder()
                .content(r.getContent())
                .target(r.getTarget())
                .type(r.getType())
                .discountRate(r.getDiscountRate())
                .saleStartDate(r.getSaleStartDate())
                .saleEndDate(r.getSaleEndDate())
                .useStartDate(r.getUseStartDate())
                .useEndDate(r.getUseEndDate())
                .note(r.getNote())
                .store(store)
                .build();
    }

    // === CRUD: Entity -> Response ===
    public PartnershipResponse toResponse(Partnership e) {
        if (e == null) return null;
        return PartnershipResponse.builder()
                .partnershipId(e.getPartnershipId())
                .content(e.getContent())
                .target(e.getTarget())
                .type(e.getType())
                .discountRate(e.getDiscountRate())
                .saleStartDate(e.getSaleStartDate())
                .saleEndDate(e.getSaleEndDate())
                .useStartDate(e.getUseStartDate())
                .useEndDate(e.getUseEndDate())
                .note(e.getNote())
                .views(e.getViews())
                .storeId(e.getStore() != null ? e.getStore().getStoreId() : null)
                .build();
    }

    public List<PartnershipResponse> toResponseList(List<Partnership> entities) {
        if (entities == null) return List.of();
        return entities.stream().filter(Objects::nonNull).map(this::toResponse).collect(Collectors.toList());
    }

    // === INFO: Entity -> InfoDto (이번에만 수정) ===
    public PartnershipInfoDto toInfo(Partnership e) {
        if (e == null) return null;
        Store s = e.getStore();
        return PartnershipInfoDto.builder()
                .partnershipId(e.getPartnershipId())
                .content(e.getContent())
                .type(e.getType())
                .storeName(s != null ? s.getStoreName() : null)
                .organization(s != null ? s.getOrganization() : null)
                .category(s != null ? s.getCategory() : null)
                .build();
    }

    public List<PartnershipInfoDto> toInfoList(List<Partnership> entities) {
        if (entities == null) return List.of();
        return entities.stream().filter(Objects::nonNull).map(this::toInfo).collect(Collectors.toList());
    }
}
