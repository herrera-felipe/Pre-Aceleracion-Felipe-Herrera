package com.disney.world.repository;

import java.util.List;

import com.disney.world.entity.MovieEntity;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    
    // Metodo para la busqueda por filtros
    public List<MovieEntity> findAll(Specification<MovieEntity> spec);
}
