package com.disney.world.service;

import java.util.List;

import com.disney.world.dto.CharacterBasicDTO;
import com.disney.world.dto.CharacterDTO;

public interface CharacterService {

    public CharacterDTO save(CharacterDTO dto);

    public List<CharacterDTO> getAllCharacters();

    public List<CharacterBasicDTO> getAllCharactersBasicData();

    public void delete(Long id);
    
}
