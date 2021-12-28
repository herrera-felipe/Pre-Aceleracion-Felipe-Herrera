package com.disney.world.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieFiltersDTO {

    private String title;
    private List<Long> genre;
    private String order;

    public MovieFiltersDTO(String title, List<Long> genre, String order) {
        this.title = title;
        this.genre = genre;
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
