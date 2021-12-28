package com.disney.world.repository.specifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import com.disney.world.dto.MovieFiltersDTO;
import com.disney.world.entity.GenreEntity;
import com.disney.world.entity.MovieEntity;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class MovieSpecification {
    
    // Recibe la lista de filtros dto
    public Specification<MovieEntity> getByFilters(MovieFiltersDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {

            // Se crea la lista de predicados
            List<Predicate> predicates = new ArrayList<>();

            // Busqueda por titulo
            if (StringUtils.hasLength(filtersDTO.getTitle())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + filtersDTO.getTitle().toLowerCase() + "%"));
            }

            // Busqueda por genero {id}
            if (!CollectionUtils.isEmpty(filtersDTO.getGenre())) {
                Join<GenreEntity, MovieEntity> join = root.join("genre", JoinType.INNER);
                Expression<String> genreId = join.get("id");
                predicates.add(genreId.in(filtersDTO.getGenre()));
            }

            // eliminar duplicados
            query.distinct(true);

            // definir orden
            String orderByField = "title";
            query.orderBy(filtersDTO.isASC() ? criteriaBuilder.asc(root.get(orderByField)) : criteriaBuilder.desc(root.get(orderByField)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
