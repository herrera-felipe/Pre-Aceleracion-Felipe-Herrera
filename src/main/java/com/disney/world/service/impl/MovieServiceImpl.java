package com.disney.world.service.impl;

import java.util.List;

import com.disney.world.dto.MovieBasicDTO;
import com.disney.world.dto.MovieDTO;
import com.disney.world.dto.MovieFiltersDTO;
import com.disney.world.entity.MovieEntity;
import com.disney.world.mapper.MovieMapper;
import com.disney.world.repository.MovieRepository;
import com.disney.world.repository.specifications.MovieSpecification;
import com.disney.world.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieMapper movieMapper;

    private MovieRepository movieRepository;

    private MovieSpecification specification;

    @Autowired
    public MovieServiceImpl(MovieMapper movieMapper, MovieRepository movieRepository, MovieSpecification specification) {
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
        this.specification = specification;
    }

    public MovieDTO save(MovieDTO dto) {
        MovieEntity entity = this.movieMapper.movieDTO2Entity(dto); // convertir dto a entidad
        MovieEntity entitySaved =  this.movieRepository.save(entity); // persistir entidad en la bd
        MovieDTO resultDTO = this.movieMapper.movieEntity2DTO(entitySaved, false); // convertir entidad guardada a dto
        return resultDTO;
    }

    public List<MovieDTO> getAllMovies() {
        List<MovieEntity> entities = this.movieRepository.findAll(); // traer las peliculas de la bd
        List<MovieDTO> resultDTOList = this.movieMapper.movieEntityList2DTOList(entities, false); // convertir la lista de entidades a lista dto
        return resultDTOList;
    }
    
    // Listado de peliculas solo campos: image, tittle, creationDate
    public List<MovieBasicDTO> getAllMoviesBasicData() {
        List<MovieEntity> entities = this.movieRepository.findAll(); // traer las peliculas de la bd
        List<MovieBasicDTO> resultDTOList = this.movieMapper.movieEntityList2BasicDTOList(entities); // convertir la lista de entidades a lista dto
        return resultDTOList;
    }

    // Busqueda por filtros
    public List<MovieDTO> getByFilters(String title, List<Long> genre, String order) {
        MovieFiltersDTO filtersDTO = new MovieFiltersDTO(title, genre, order); // Intanciar el DTO con los filtros
        List<MovieEntity> entities = this.movieRepository.findAll(this.specification.getByFilters(filtersDTO)); // Busqueda segun filtros
        List<MovieDTO> resultDTOList = this.movieMapper.movieEntityList2DTOList(entities, false); // convertir a lista dto
        return resultDTOList;
    }

    // Metodo para el softDelete mediante anotacion sql en la entidad
    public void delete(Long id) {
        this.movieRepository.deleteById(id);
    }
}
