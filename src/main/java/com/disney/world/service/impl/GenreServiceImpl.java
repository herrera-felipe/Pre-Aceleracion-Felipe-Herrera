package com.disney.world.service.impl;

import java.util.List;

import com.disney.world.dto.GenreDTO;
import com.disney.world.entity.GenreEntity;
import com.disney.world.mapper.GenreMapper;
import com.disney.world.repository.GenreRepository;
import com.disney.world.service.GenreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreMapper genreMapper;

    private GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreMapper genreMapper, GenreRepository genreRepository) {
        this.genreMapper = genreMapper;
        this.genreRepository = genreRepository;
    }


    public GenreDTO save(GenreDTO dto) {
        GenreEntity entity = this.genreMapper.genreDTO2Entity(dto); // convertir dto a entidad
        GenreEntity entitySaved = this.genreRepository.save(entity); // persistir en la base de datos
        GenreDTO resultDto = this.genreMapper.genreEntity2DTO(entitySaved); // convertir entidad guardada a dto
        return resultDto;
    }

    public List<GenreDTO> getAllGenres() {
        List<GenreEntity> entities = this.genreRepository.findAll(); // Traemos todos los generos de la bd
        List<GenreDTO> resultDTOList = this.genreMapper.genreEntityList2DTOList(entities);
        return resultDTOList;
    }

}
