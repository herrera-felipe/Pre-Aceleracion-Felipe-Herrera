package com.disney.world.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTO {

    private Long id;

    private String image;

    private String title;

    private String creationDate;

    private Integer qualification;

    private Long genreId;

    private GenreDTO genre;

    private List<CharacterDTO> characters;
    
}
