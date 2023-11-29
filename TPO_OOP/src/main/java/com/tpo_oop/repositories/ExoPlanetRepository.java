package com.tpo_oop.repositories;

import com.tpo_oop.entities.ExoPlanet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExoPlanetRepository extends JpaRepository<ExoPlanet, Integer> {
}
