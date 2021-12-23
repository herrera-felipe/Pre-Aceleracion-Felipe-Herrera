package com.disney.world.service;

import java.util.List;

import com.disney.world.dto.MovieBasicDTO;
import com.disney.world.dto.MovieDTO;

public interface MovieService {

    public MovieDTO save(MovieDTO dto);

    public List<MovieDTO> getAllMovies();

    public List<MovieBasicDTO> getAllMoviesBasicData();

    public void delete(Long id);
    
}
