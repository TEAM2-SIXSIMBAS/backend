// src/main/java/org/example/schoolallianceinfor/repository/StoreRepository.java
package org.example.schoolallianceinfor.repository;

import org.example.schoolallianceinfor.dto.store.StoreMarkerDto;
import org.example.schoolallianceinfor.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {

    @Query("select new org.example.schoolallianceinfor.dto.store.StoreMarkerDto(s.storeName, l.lat, l.lng) " +
            "from Store s join s.location l " +
            "where l.lat is not null and l.lng is not null " +
            "and (:name is null or lower(s.storeName) like lower(concat('%', :name, '%')))")
    Page<StoreMarkerDto> findMarkers(@Param("name") String name, Pageable pageable);

    // ✅ 서비스에서 호출하는 이름 그대로 유지 (location 즉시 로딩)
    @EntityGraph(attributePaths = "location")
    List<Store> findByStoreNameIn(Collection<String> names);

    // ✅ 검색: storeName 부분일치 + 대소문자 무시 + 페이징/정렬 지원
    Page<Store> findByStoreNameContainingIgnoreCase(String storeName, Pageable pageable);
}
