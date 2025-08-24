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
                .partnershipImageUrl(r.getPartnershipImageUrl()) // ✅ 컨트롤러가 주입한 URL 매핑
                .store(store)
                .views(r.getViews() != null ? r.getViews() : 0) // ✅ 요청 값 우선, 없으면 0
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
                .partnershipImageUrl(e.getPartnershipImageUrl()) // 이미 반영되어 있던 부분
                .build();
    }

    public List<PartnershipResponse> toResponseList(List<Partnership> entities) {
        if (entities == null) return List.of();
        return entities.stream().filter(Objects::nonNull).map(this::toResponse).collect(Collectors.toList());
    }

    // === INFO: Entity -> InfoDto ===
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
                .partnershipImageUrl(e.getPartnershipImageUrl()) // 리스트/요약에도 포함
                .build();
    }

    public List<PartnershipInfoDto> toInfoList(List<Partnership> entities) {
        if (entities == null) return List.of();
        return entities.stream().filter(Objects::nonNull).map(this::toInfo).collect(Collectors.toList());
    }
}
