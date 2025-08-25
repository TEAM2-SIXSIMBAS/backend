package org.example.schoolallianceinfor.repository;

import org.example.schoolallianceinfor.entity.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartnershipRepository extends JpaRepository<Partnership, Integer> {

    // Partnership → Store → StoreLocation 을 한 번에 로딩 (N+1 방지)
    @Query("""
       select p
       from Partnership p
       join fetch p.store s
       left join fetch s.location l
    """)
    List<Partnership> findAllWithStoreAndLocation();
}
