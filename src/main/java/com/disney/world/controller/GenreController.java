package com.disney.world.controller;

import java.util.List;

import com.disney.world.dto.GenreDTO;
import com.disney.world.service.GenreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    // EndPoint para obtener los generos de peliculas
    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAll()  {
        List<GenreDTO> genreList = this.genreService.getAllGenres();
        return ResponseEntity.ok().body(genreList);
    }

    // EndPoint para guardar generos de peliculas
    @PostMapping
    public ResponseEntity<GenreDTO> save(@RequestBody GenreDTO dto){
        GenreDTO genreSaved = this.genreService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(genreSaved);
    }
    
}
