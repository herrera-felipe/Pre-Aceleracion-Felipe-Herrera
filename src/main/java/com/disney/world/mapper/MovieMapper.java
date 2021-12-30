package com.disney.world.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.disney.world.dto.CharacterDTO;
import com.disney.world.dto.MovieBasicDTO;
import com.disney.world.dto.MovieDTO;
import com.disney.world.entity.GenreEntity;
import com.disney.world.entity.MovieEntity;
import com.disney.world.exception.ParamNotFound;
import com.disney.world.repository.GenreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    private GenreRepository genreRepository;

    private GenreMapper genreMapper;

    private CharacterMapper characterMapper;

    @Autowired
    public MovieMapper(@Lazy GenreRepository genreRepository, GenreMapper genreMapper, CharacterMapper characterMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
        this.characterMapper = characterMapper;
    }

    // convertir dto a entidad
    public MovieEntity movieDTO2Entity(MovieDTO dto) {
        MovieEntity entity = new MovieEntity();
        entity.setImage(dto.getImage());
        entity.setTitle(dto.getTitle());
        entity.setCreationDate(this.string2LocalDate(dto.getCreationDate()));
        entity.setQualification(dto.getQualification());
        entity.setGenreId(dto.getGenreId());

        // Buscamos el si existe el genero por el Id de estar Ok seteamos
        Optional<GenreEntity> resultGenre = genreRepository.findById(entity.getGenreId());
        if (resultGenre.isPresent()) {
            GenreEntity genre = resultGenre.get();
            entity.setGenre(genre);
        } else {
            throw new ParamNotFound("Invalid id Genre.");
        }
        return entity;
    }

    // convertir entidad a dto
    public MovieDTO movieEntity2DTO(MovieEntity entity, boolean loadCharacters) {
        MovieDTO dto = new MovieDTO();
        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setTitle(entity.getTitle());
        dto.setCreationDate(entity.getCreationDate().toString());
        dto.setQualification(entity.getQualification());
        dto.setGenreId(entity.getGenreId());
        dto.setGenre(this.genreMapper.genreEntity2DTO(entity.getGenre()));
        // validacion para cargar o no los personajes
        if (loadCharacters) {
            List<CharacterDTO> characterList = this.characterMapper.characterEntityList2DTOList(entity.getCharacters(), false);
            dto.setCharacters(characterList);
        }
        return dto;
    }

    // convertir entidad a basic dto
    public MovieBasicDTO movieEntity2BasicDTO(MovieEntity entity) {
        MovieBasicDTO basicDTO = new MovieBasicDTO();
        basicDTO.setImage(entity.getImage());
        basicDTO.setTitle(entity.getTitle());
        basicDTO.setCreationDate(entity.getCreationDate().toString());
        return basicDTO;
    }

     // Formateador de String a LocalDate
     private LocalDate string2LocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }

    // convierte una lista de entidades a lista dto
    public List<MovieDTO> movieEntityList2DTOList(List<MovieEntity> entities, boolean loadCharacters) {
        List<MovieDTO> resultDTOList = new ArrayList<>();
        for (MovieEntity entity : entities) {
            resultDTOList.add(this.movieEntity2DTO(entity, loadCharacters));
        }
        return resultDTOList;
    }
    
    // convierte una lista de entidades en una listaBasicDTO
    public List<MovieBasicDTO> movieEntityList2BasicDTOList(List<MovieEntity> entities) {
        List<MovieBasicDTO> resultBasicDTOList = new ArrayList<>();
        for (MovieEntity entity : entities) {
            resultBasicDTOList.add(this.movieEntity2BasicDTO(entity));
        }
        return resultBasicDTOList;
    }

    // Modificar Movie
    public void movieEntityRefreshValues(MovieEntity entity, MovieDTO dto) {
        entity.setImage(dto.getImage());
        entity.setTitle(dto.getTitle());
        entity.setCreationDate(this.string2LocalDate(dto.getCreationDate()));
        entity.setQualification(dto.getQualification());
        entity.setGenreId(dto.getGenreId());

        // Buscamos si existe el genero por el Id de estar Ok seteamos
        Optional<GenreEntity> resultGenre = genreRepository.findById(entity.getGenreId());
        if (resultGenre.isPresent()) {
            entity.setGenre(resultGenre.get());
        } else {
            throw new ParamNotFound("Invalid id Genre.");
        }
    }

}
