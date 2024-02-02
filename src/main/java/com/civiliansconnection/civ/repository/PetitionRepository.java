package com.civiliansconnection.civ.repository;

import com.civiliansconnection.civ.domain.Petition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionRepository extends JpaRepository<Petition, Long> {

}
