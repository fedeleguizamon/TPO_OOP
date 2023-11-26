package com.tpo_oop.services;

import com.tpo_oop.entities.Telescope;
import com.tpo_oop.repositories.TelescopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelescopeService implements BaseService<Telescope> {
    @Autowired
    private TelescopeRepository telescopeRepository;

    @Override
    public List<Telescope> findAll() throws Exception {
        try {
            List<Telescope> entities = this.telescopeRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Telescope findById(Integer id) throws Exception {
        try {
            Optional<Telescope> entityOptinal = this.telescopeRepository.findById(id);
            return entityOptinal.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Telescope save(Telescope entity) throws Exception {
        try {
            entity = this.telescopeRepository.save(entity);
            return entity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Telescope update(Integer id, Telescope entity) throws Exception {
        try {
            Optional<Telescope> entityOptional = this.telescopeRepository.findById(id);
            Telescope telescope = entityOptional.get();
            telescope = this.telescopeRepository.save(entity);
            return telescope;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        try {
            if (this.telescopeRepository.existsById(id)) {
                this.telescopeRepository.deleteById(id);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
