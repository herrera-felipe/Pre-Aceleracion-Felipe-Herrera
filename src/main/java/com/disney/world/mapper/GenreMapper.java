package com.disney.world.mapper;

import java.util.ArrayList;
import java.util.List;

import com.disney.world.dto.GenreDTO;
import com.disney.world.entity.GenreEntity;

import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    // convertir dto a entidad
    public GenreEntity genreDTO2Entity(GenreDTO dto) {
        GenreEntity entity = new GenreEntity();
        entity.setName(dto.getName());
        entity.setImage(dto.getImage()); 
        return entity;
    }

    // convertir entidad a dto
    public GenreDTO genreEntity2DTO(GenreEntity entity) {
        GenreDTO dto = new GenreDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        return dto;
    }

    // convertir lista de entidades, a una lista dto
    public List<GenreDTO> genreEntityList2DTOList(List<GenreEntity> entities) {
        List<GenreDTO> resultDTOList = new ArrayList<>();
        for (GenreEntity entity : entities) {
            resultDTOList.add(this.genreEntity2DTO(entity));
        }
        return resultDTOList;
    }

}
