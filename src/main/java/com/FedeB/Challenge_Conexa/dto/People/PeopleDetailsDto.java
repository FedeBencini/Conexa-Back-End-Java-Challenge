package com.FedeB.Challenge_Conexa.dto.People;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO que representa los detalles específicos de un personaje en la API de SWAPI.
 * <p>
 * Este objeto contiene información relevante sobre un personaje, como su nombre, género,
 * características físicas, altura, peso, fecha de nacimiento y listas de vehículos, naves
 * y películas asociadas.
 */
@Data
@NoArgsConstructor
public class PeopleDetailsDto {
    private String created;
    private String edited;
    private String name;
    private String gender;
    private String skin_color;
    private String hair_color;
    private String height;
    private String eye_color;
    private String mass;
    private String birth_year;
    private List<String> vehicles;
    private List<String> starships;
    private List<String> films;
    private String url;
}
