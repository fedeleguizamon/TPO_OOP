package com.tpo_oop.services;

import com.tpo_oop.entities.ExoPlanet;
import com.tpo_oop.repositories.ExoPlanetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExoPlanetService implements BaseService<ExoPlanet> {
    @Autowired
    private ExoPlanetRepository exoPlanetRepository;

    @Override
    @Transactional
    public List<ExoPlanet> findAll() throws Exception {
        try {
            List<ExoPlanet> entities = this.exoPlanetRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ExoPlanet findById(Integer id) throws Exception {
        try {
            Optional<ExoPlanet> entityOptional = this.exoPlanetRepository.findById(id);
            return entityOptional.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ExoPlanet save(ExoPlanet entity) throws Exception {
        try {
            entity = this.exoPlanetRepository.save(entity);
            return entity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ExoPlanet update(Integer id, ExoPlanet entity) throws Exception {
        try {
            Optional<ExoPlanet> entityOptional = this.exoPlanetRepository.findById(id);
            ExoPlanet exoPlanet = entityOptional.get();
            exoPlanet = this.exoPlanetRepository.save(entity);
            return exoPlanet;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Integer id) throws Exception {
        try {
            if (this.exoPlanetRepository.existsById(id)) {
                this.exoPlanetRepository.deleteById(id);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
