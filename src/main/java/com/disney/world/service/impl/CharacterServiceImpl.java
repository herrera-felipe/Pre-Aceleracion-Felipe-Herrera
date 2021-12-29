package com.disney.world.service.impl;

import java.util.List;
import java.util.Optional;

import com.disney.world.dto.CharacterBasicDTO;
import com.disney.world.dto.CharacterDTO;
import com.disney.world.dto.CharacterFiltersDTO;
import com.disney.world.entity.CharacterEntity;
import com.disney.world.mapper.CharacterMapper;
import com.disney.world.repository.CharacterRepository;
import com.disney.world.repository.specifications.CharacterSpecification;
import com.disney.world.service.CharacterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterServiceImpl implements CharacterService {

    private CharacterMapper characterMapper;

    private CharacterRepository characterRepository;

    private CharacterSpecification specification;

    @Autowired
    public CharacterServiceImpl(CharacterMapper characterMapper, CharacterRepository characterRepository, CharacterSpecification specification) {
        this.characterMapper = characterMapper;
        this.characterRepository = characterRepository;
        this.specification = specification;
    }

    public CharacterDTO save(CharacterDTO dto) {
        CharacterEntity entity = this.characterMapper.characterDTO2Entity(dto); // convertir dto a entidad
        CharacterEntity entitySaved = this.characterRepository.save(entity); // persistir entidad en BD
        CharacterDTO resultDTO = this.characterMapper.characterEntity2DTO(entitySaved, false); // convertir entidad en dto
        return resultDTO;
    }

    public CharacterDTO update(Long id, CharacterDTO dto) {
        Optional<CharacterEntity> entity = this.characterRepository.findById(id); // Buscar el Character a modificar
        // validar si existe
        if (!entity.isPresent()) {
            //throw new ParamNotFound("Invalid character id.")
        }
        
        this.characterMapper.characterEntityRefreshValues(entity.get(), dto); // Modificar
        CharacterEntity entitySaved = this.characterRepository.save(entity.get()); // Persistir
        CharacterDTO resultDTO = this.characterMapper.characterEntity2DTO(entitySaved, false); // Convertir a DTO
        return resultDTO;    
    }

    public List<CharacterDTO> getAllCharacters() {
        List<CharacterEntity> entities = this.characterRepository.findAll(); 
        List<CharacterDTO> resultDTOList = this.characterMapper.characterEntityList2DTOList(entities, false);
        return resultDTOList;
    }

    // Listara los personajes con solo 3 atributos
    public List<CharacterBasicDTO> getAllCharactersBasicData() {
        List<CharacterEntity> entities = this.characterRepository.findAll();
        List<CharacterBasicDTO> resultDTOList = this.characterMapper.characterEntityList2BasicDTOList(entities);
        return resultDTOList;
    }

    // Metodo para la busqueda por filtros
    public List<CharacterDTO> getByFilters(String name, Integer age, List<Long> movies, String order) {
        CharacterFiltersDTO filtersDTO = new CharacterFiltersDTO(name, age, movies, order); // Instanciamos el dto con los filtros
        List<CharacterEntity> entities = this.characterRepository.findAll(this.specification.getByFilters(filtersDTO)); // Buscamos segun filtros
        List<CharacterDTO> resultDTOList = this.characterMapper.characterEntityList2DTOList(entities, true); // Convertimos la lista a DTO
        return resultDTOList;
    }

    // Metodo para el softDelete mediante la anotacion SQL en la entidad
    public void delete(Long id) {
        this.characterRepository.deleteById(id);
    }

}
