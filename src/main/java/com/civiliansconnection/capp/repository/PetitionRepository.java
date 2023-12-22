package com.civiliansconnection.capp.repository;

import com.civiliansconnection.capp.domain.Petition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionRepository extends JpaRepository<Petition, Long> {

}
