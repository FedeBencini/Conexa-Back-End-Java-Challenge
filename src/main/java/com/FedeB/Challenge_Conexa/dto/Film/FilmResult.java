package com.FedeB.Challenge_Conexa.dto.Film;

import com.FedeB.Challenge_Conexa.dto.People.PeopleDetailsDto;
import lombok.Data;

/**
 * DTO que representa el resultado de una solicitud de película desde la API de SWAPI.
 * <p>
 * Este objeto encapsula los detalles de una película, incluyendo sus propiedades principales,
 * identificadores únicos y metadatos adicionales como descripción y versión.
 */
@Data
public class FilmResult {
    private FilmDetailsDto properties;
    private String _id;
    private String description;
    private String uid;
    private String __v;
}