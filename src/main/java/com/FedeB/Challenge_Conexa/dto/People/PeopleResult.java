package com.FedeB.Challenge_Conexa.dto.People;

import lombok.Data;

/**
 * DTO que representa el resultado de una solicitud de personaje desde la API de SWAPI.
 * <p>
 * Este objeto encapsula los detalles de un personaje, incluyendo sus propiedades principales,
 * identificadores únicos y metadatos adicionales como descripción y versión.
 */
@Data
public class PeopleResult {
    private PeopleDetailsDto properties;
    private String _id;
    private String description;
    private String uid;
    private String __v;
}