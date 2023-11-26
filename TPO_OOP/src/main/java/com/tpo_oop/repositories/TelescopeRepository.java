package com.tpo_oop.repositories;

import com.tpo_oop.entities.Telescope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelescopeRepository extends JpaRepository<Telescope, Integer> {
}
