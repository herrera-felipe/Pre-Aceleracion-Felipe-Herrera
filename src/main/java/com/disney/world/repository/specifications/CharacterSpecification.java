package com.disney.world.repository.specifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import com.disney.world.dto.CharacterFiltersDTO;
import com.disney.world.entity.CharacterEntity;
import com.disney.world.entity.MovieEntity;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class CharacterSpecification {

    // Le llega la lista de filtros DTO
    public Specification<CharacterEntity> getByFilters(CharacterFiltersDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {

            // se crea una lista de predicados
            List<Predicate> predicates = new ArrayList<>();

            // Busqueda por nombre
            if (StringUtils.hasLength(filtersDTO.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filtersDTO.getName().toLowerCase() + "%"));
            }

            // Busqueda por edad
            if (filtersDTO.getAge() != null) {
                predicates.add(criteriaBuilder.equal(root.get("age"), filtersDTO.getAge()));
            }

            // Busqueda por pelicula {id}
            if (!CollectionUtils.isEmpty(filtersDTO.getMovies())) {
                Join<MovieEntity, CharacterEntity> join = root.join("movies", JoinType.INNER);
                Expression<String> moviesId = join.get("id");
                predicates.add(moviesId.in(filtersDTO.getMovies()));
            }

            // Eliminar duplicados
            query.distinct(true);

            // Definir el orden
            String orderByField = "name";
            query.orderBy(filtersDTO.isASC() ? criteriaBuilder.asc(root.get(orderByField)) : criteriaBuilder.desc(root.get(orderByField)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
