package com.civiliansconnection.capp.repository;

import com.civiliansconnection.capp.domain.Petition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PetitionRepository extends JpaRepository<Petition, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE PETITION SET NUMBER_OF_SIGNATURES = NUMBER_OF_SIGNATURES + 1 WHERE ID = :id", nativeQuery = true)
    void addSignature(@Param("id") Long id);
}
