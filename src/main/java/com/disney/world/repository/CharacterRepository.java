package com.disney.world.repository;

import java.util.List;

import com.disney.world.entity.CharacterEntity;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
    
    // Metodo para la busqueda por filtros
    public List<CharacterEntity> findAll(Specification<CharacterEntity> spec);
}
