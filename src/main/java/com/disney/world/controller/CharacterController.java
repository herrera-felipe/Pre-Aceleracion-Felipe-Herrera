package com.disney.world.controller;

import java.util.List;

import com.disney.world.dto.CharacterBasicDTO;
import com.disney.world.dto.CharacterDTO;
import com.disney.world.service.CharacterService;

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
@RequestMapping("characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping("/details")
    public ResponseEntity<List<CharacterDTO>> getAll() {
        List<CharacterDTO> characterList = this.characterService.getAllCharacters();
        return ResponseEntity.ok().body(characterList);
    }

    @GetMapping
    public ResponseEntity<List<CharacterBasicDTO>> getAllBasicData() {
        List<CharacterBasicDTO> characterBasicList = this.characterService.getAllCharactersBasicData();
        return ResponseEntity.ok().body(characterBasicList);
    }

     // Busqueda por filtros
     @GetMapping("/filters")
     public ResponseEntity<List<CharacterDTO>> getDetailsByFilters(
             @RequestParam(required = false) String name,
             @RequestParam(required = false) Integer age,
             @RequestParam(required = false) List<Long> movies,
             @RequestParam(required = false, defaultValue = "ASC") String order) {
 
         List<CharacterDTO> characters = this.characterService.getByFilters(name, age, movies, order);
         return ResponseEntity.ok(characters);
     }

    @PostMapping
    public ResponseEntity<CharacterDTO> save(@RequestBody CharacterDTO dto) {
        CharacterDTO characterSaved = this.characterService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(characterSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterDTO> update(@PathVariable Long id, @RequestBody CharacterDTO dto) {
        CharacterDTO resultDTO = this.characterService.update(id, dto);
        return ResponseEntity.ok().body(resultDTO);
    }

    // EndPoint para el Soft Delete mediante id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.characterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/movie/{idMovie}")
    public ResponseEntity<Void> addMovie(@PathVariable Long id, @PathVariable Long idMovie) {
        this.characterService.addMovie(id, idMovie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/movie/{idMovie}")
    public ResponseEntity<Void> removeMovie(@PathVariable Long id, @PathVariable Long idMovie) {
        this.characterService.removeMovie(id, idMovie);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
