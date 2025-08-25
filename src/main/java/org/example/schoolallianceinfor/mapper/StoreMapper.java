// src/main/java/org/example/schoolallianceinfor/mapper/StoreMapper.java
package org.example.schoolallianceinfor.mapper;

import org.example.schoolallianceinfor.dto.store.StoreInfoDto;
import org.example.schoolallianceinfor.dto.store.StoreRequest;
import org.example.schoolallianceinfor.dto.store.StoreResponse;
import org.example.schoolallianceinfor.entity.Store;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class StoreMapper {

    // ===== CRUD: Request -> Entity (create) =====
    public Store toEntity(StoreRequest r) {
        if (r == null) return null;
        return Store.builder()
                .storeName(r.getStoreName())
                .phoneNumber(r.getPhoneNumber())
                .email(r.getEmail())
                .address(r.getAddress())
                .openTime(r.getOpenTime())       // LocalTime
                .closeTime(r.getCloseTime())     // LocalTime
                .organization(r.getOrganization())
                .category(r.getCategory())
                .parkingAvailable(trimOrNull(r.getParkingAvailable())) // "가능" / "불가능"
                // partnerships 는 외부에서 관리 (연관관계 주인: Partnership.store)
                .build();
    }

    // ===== CRUD: 부분 수정용 (optional) =====
    public void updateEntity(Store target, StoreRequest r) {
        if (target == null || r == null) return;
        if (r.getStoreName() != null)     target.setStoreName(r.getStoreName());
        if (r.getPhoneNumber() != null)   target.setPhoneNumber(r.getPhoneNumber());
        if (r.getEmail() != null)         target.setEmail(r.getEmail());
        if (r.getAddress() != null)       target.setAddress(r.getAddress());
        if (r.getOpenTime() != null)      target.setOpenTime(r.getOpenTime());
        if (r.getCloseTime() != null)     target.setCloseTime(r.getCloseTime());
        if (r.getOrganization() != null)  target.setOrganization(r.getOrganization());
        if (r.getCategory() != null)      target.setCategory(r.getCategory());
        if (r.getParkingAvailable() != null)
            target.setParkingAvailable(trimOrNull(r.getParkingAvailable())); // "가능"/"불가능"
    }

    // ===== CRUD: Entity -> Response =====
    public StoreResponse toResponse(Store e) {
        if (e == null) return null;
        return StoreResponse.builder()
                .storeId(e.getStoreId())
                .storeName(e.getStoreName())
                .phoneNumber(e.getPhoneNumber())
                .email(e.getEmail())
                .address(e.getAddress())
                .openTime(e.getOpenTime())
                .closeTime(e.getCloseTime())
                .organization(e.getOrganization())
                .category(e.getCategory())
                .parkingAvailable(e.getParkingAvailable()) // 그대로 반환
                .build();
    }

    public List<StoreResponse> toResponseList(List<Store> entities) {
        if (entities == null || entities.isEmpty()) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(this::toResponse)
                .toList();
    }

    // ===== INFO: Entity -> StoreInfoDto (요약) =====
    public StoreInfoDto toInfo(Store e) {
        if (e == null) return null;
        return StoreInfoDto.builder()
                .storeId(e.getStoreId())
                .storeName(e.getStoreName())
                .phoneNumber(e.getPhoneNumber())
                .email(e.getEmail())
                .address(e.getAddress())
                .startTime(e.getOpenTime())     // 엔티티의 openTime → DTO의 startTime
                .endTime(e.getCloseTime())      // 엔티티의 closeTime → DTO의 endTime
                .parkingAvailable(e.getParkingAvailable())
                .build();
    }


    public List<StoreInfoDto> toInfoList(List<Store> entities) {
        if (entities == null || entities.isEmpty()) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(this::toInfo)
                .toList();
    }

    // ===== util =====
    private String trimOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
