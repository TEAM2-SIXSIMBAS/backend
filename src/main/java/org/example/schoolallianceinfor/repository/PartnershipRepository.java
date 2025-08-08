package org.example.schoolallianceinfor.repository;

import org.example.schoolallianceinfor.domain.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnershipRepository extends JpaRepository<Partnership, Integer> {
    // 여기서부터 필요한 custom 메서드 추가 가능
}
