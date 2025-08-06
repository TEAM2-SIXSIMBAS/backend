package org.example.schoolallianceinfor.repository;


import org.example.schoolallianceinfor.entity.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnershipRepository extends JpaRepository<Partnership, Integer> {
    
}

