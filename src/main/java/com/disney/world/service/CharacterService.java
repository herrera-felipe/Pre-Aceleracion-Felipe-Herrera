package com.disney.world.service;

import java.util.List;

import com.disney.world.dto.CharacterBasicDTO;
import com.disney.world.dto.CharacterDTO;

public interface CharacterService {

    public CharacterDTO save(CharacterDTO dto);

    public CharacterDTO update(Long id, CharacterDTO dto);

    public void delete(Long id);

    public List<CharacterDTO> getAllCharacters();

    public List<CharacterBasicDTO> getAllCharactersBasicData();

    public List<CharacterDTO> getByFilters(String name, Integer age, List<Long> movies, String order);

    public void addMovie(Long id, Long idMovie);

    public void removeMovie(Long id, Long idMovie);

}
