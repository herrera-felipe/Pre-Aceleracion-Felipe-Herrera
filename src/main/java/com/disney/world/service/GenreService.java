package com.disney.world.service;

import java.util.List;

import com.disney.world.dto.GenreDTO;

public interface GenreService {

    public GenreDTO save(GenreDTO dto);

    public List<GenreDTO> getAllGenres();
    
}
