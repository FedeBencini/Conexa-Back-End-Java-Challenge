package com.FedeB.Challenge_Conexa.dto.Starship;

import com.FedeB.Challenge_Conexa.dto.People.PeopleDetailsDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO que representa el resultado de una solicitud de nave desde la API de SWAPI.
 * <p>
 * Este objeto encapsula los detalles de un nave, incluyendo sus propiedades principales,
 * identificadores únicos y metadatos adicionales como descripción y versión.
 */
@Data
@NoArgsConstructor
public class StarshipResult {
    private StarshipDetailsDto properties;
    private String _id;
    private String description;
    private String uid;
    private String __v;
}
