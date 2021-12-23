package com.disney.world.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`character`")
@Getter
@Setter
@SQLDelete(sql = "UPDATE `character` SET deleted=true WHERE id=?") // anotacion para el softDelete
@Where(clause = "deleted=false")
public class CharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String image;

    private String name;

    private Integer age;

    private Float weight;
    
    private String story;

    private boolean deleted = Boolean.FALSE; // atributo para el softDelete

    // Relacion de entidades
    // Character -> Movie : muchos personajes pueden ser parte de muchas peliculas
    @ManyToMany(mappedBy = "characters", cascade = CascadeType.ALL)
    private List<MovieEntity> movies = new ArrayList<>();

    // Agregar o quitar pelicula
    public void addMovie(MovieEntity entity) {
        this.movies.add(entity);
    }

    public void removeMovie(MovieEntity entity) {
        this.movies.remove(entity);
    }
    
}
