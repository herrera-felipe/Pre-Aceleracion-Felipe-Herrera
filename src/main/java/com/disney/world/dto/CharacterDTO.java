package com.disney.world.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterDTO {
    
    private Long id;

    private String image;

    private String name;

    private Integer age;

    private Float weight;
    
    private String story;

    private List<MovieDTO> movies;
}
