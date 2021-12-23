package com.disney.world.service.impl;

import java.util.List;

import com.disney.world.dto.CharacterBasicDTO;
import com.disney.world.dto.CharacterDTO;
import com.disney.world.entity.CharacterEntity;
import com.disney.world.mapper.CharacterMapper;
import com.disney.world.repository.CharacterRepository;
import com.disney.world.service.CharacterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterServiceImpl implements CharacterService {

    private CharacterMapper characterMapper;

    private CharacterRepository characterRepository;

    @Autowired
    public CharacterServiceImpl(CharacterMapper characterMapper, CharacterRepository characterRepository) {
        this.characterMapper = characterMapper;
        this.characterRepository = characterRepository;
    }

    public CharacterDTO save(CharacterDTO dto) {
        CharacterEntity entity = this.characterMapper.characterDTO2Entity(dto); // convertir dto a entidad
        CharacterEntity entitySaved = this.characterRepository.save(entity); // persistir entidad en BD
        CharacterDTO resultDTO = this.characterMapper.characterEntity2DTO(entitySaved, false); // convertir entidad en dto
        return resultDTO;
    }

    public List<CharacterDTO> getAllCharacters() {
        List<CharacterEntity> entities = this.characterRepository.findAll(); 
        List<CharacterDTO> resultDTOList = this.characterMapper.characterEntityList2DTOList(entities);
        return resultDTOList;
    }

    // Listara los personajes con solo 3 atributos
    public List<CharacterBasicDTO> getAllCharactersBasicData() {
        List<CharacterEntity> entities = this.characterRepository.findAll();
        List<CharacterBasicDTO> resultDTOList = this.characterMapper.characterEntityList2BasicDTOList(entities);
        return resultDTOList;
    }

    // Metodo para el softDelete mediante la anotacion SQL en la entidad
    public void delete(Long id) {
        this.characterRepository.deleteById(id);
    }

}
