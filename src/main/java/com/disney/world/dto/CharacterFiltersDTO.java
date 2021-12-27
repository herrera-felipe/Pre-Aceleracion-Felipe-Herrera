package com.disney.world.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterFiltersDTO {

    private String name;
    private Integer age;
    private List<Long> movies;
    private String order;

    public CharacterFiltersDTO(String name, Integer age, List<Long> movies, String order) {
        this.name = name;
        this.age = age;
        this.movies = movies;
        this.order = order;
    }
    
    // Orden ascendente
    public boolean isASC() {
        return this.order.compareToIgnoreCase("ASC") == 0;
    }

    // orden descendente
    public boolean isDESC() {
        return this.order.compareToIgnoreCase("DESC") == 0;
    }
    
}
