package com.tpo_oop.repositories;

import com.tpo_oop.entities.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SatelliteRepository extends JpaRepository<Satellite, Integer> {
}
