package com.disney.world.controller;

import java.util.List;

import com.disney.world.dto.MovieBasicDTO;
import com.disney.world.dto.MovieDTO;
import com.disney.world.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // EndPoint para obtener las peliculas con todos los atributos
    @GetMapping("details")
    public ResponseEntity<List<MovieDTO>> getAll() {
        List<MovieDTO> movieList = this.movieService.getAllMovies();
        return ResponseEntity.ok().body(movieList);
    }

    // EndPoint para obtener las peliculas solo campos: image, title, creationDate
    @GetMapping
    public ResponseEntity<List<MovieBasicDTO>> getAllBasic(){
        List<MovieBasicDTO> movieList = this.movieService.getAllMoviesBasicData();
        return ResponseEntity.ok().body(movieList);
    }

    // Busqueda por filtros
    @GetMapping("filters")
    public ResponseEntity<List<MovieDTO>> getDetailsByFilters(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) List<Long> genre,
        @RequestParam(required = false, defaultValue = "ASC") String order) {

            List<MovieDTO> movies = this.movieService.getByFilters(title, genre, order);
            return ResponseEntity.ok(movies);
        }

    @PostMapping
    public ResponseEntity<MovieDTO> save(@RequestBody MovieDTO dto){
        MovieDTO movieSaved = this.movieService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @RequestBody MovieDTO dto) {
        MovieDTO resultDTO =this.movieService.update(id, dto);
        return ResponseEntity.ok().body(resultDTO);
    }

    // EndPoint para el softDelete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.movieService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
