package com.disney.world.mapper;

import java.util.ArrayList;
import java.util.List;

import com.disney.world.dto.CharacterBasicDTO;
import com.disney.world.dto.CharacterDTO;
import com.disney.world.dto.MovieDTO;
import com.disney.world.entity.CharacterEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper {

    private MovieMapper movieMapper;

    @Autowired
    public CharacterMapper(@Lazy MovieMapper movieMapper) {
        this.movieMapper = movieMapper;
    }

    // convertir dto a entidad
    public CharacterEntity characterDTO2Entity(CharacterDTO dto) {
        CharacterEntity entity = new CharacterEntity();
        entity.setImage(dto.getImage());
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setWeight(dto.getWeight());
        entity.setStory(dto.getStory());
        return entity;
    }

    // convertir entidad a dto
    public CharacterDTO characterEntity2DTO(CharacterEntity entity, boolean loadMovies) {
        CharacterDTO dto = new CharacterDTO();
        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        dto.setWeight(entity.getWeight());
        dto.setStory(entity.getStory());
        // validar si se cargan las peliculas
        if (loadMovies) {
            List<MovieDTO> movieList = this.movieMapper.movieEntityList2DTOList(entity.getMovies());
            dto.setMovies(movieList);
        }
        return dto;
    }

    // convertir entidad a basic dto
    public CharacterBasicDTO characterEntity2BasicDTO(CharacterEntity entity) {
        CharacterBasicDTO basicDTO = new CharacterBasicDTO();
        basicDTO.setImage(entity.getImage());
        basicDTO.setName(entity.getName());
        return basicDTO;
    }

    // convertir lista entidad a lista dto
    public List<CharacterDTO> characterEntityList2DTOList(List<CharacterEntity> entities) {
        List<CharacterDTO> resultDTOList = new ArrayList<>();
        for (CharacterEntity entity : entities) {
            resultDTOList.add(this.characterEntity2DTO(entity, false));
        }
        return resultDTOList;
    }

    // convertir lista entidad a lista basic dto
    public List<CharacterBasicDTO> characterEntityList2BasicDTOList(List<CharacterEntity> entities) {
        List<CharacterBasicDTO> resultDTOList = new ArrayList<>();
        for (CharacterEntity entity : entities) {
            resultDTOList.add(this.characterEntity2BasicDTO(entity));
        }
        return resultDTOList;
    }
    
}
