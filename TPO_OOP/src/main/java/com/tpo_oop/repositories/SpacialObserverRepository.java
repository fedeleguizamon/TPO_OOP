package com.tpo_oop.repositories;

import com.tpo_oop.entities.SpacialObserver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpacialObserverRepository extends JpaRepository<SpacialObserver, Integer> {
}
