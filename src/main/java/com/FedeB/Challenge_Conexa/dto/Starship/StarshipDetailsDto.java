package com.FedeB.Challenge_Conexa.dto.Starship;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO que representa los detalles específicos de una nave espacial en la API de SWAPI.
 * <p>
 * Este objeto contiene información relevante sobre una nave espacial, como su nombre, modelo,
 * clase, fabricante, dimensiones, capacidad de carga, velocidad máxima en la atmósfera,
 * calificación de hiperimpulsor, y listas de películas y pilotos asociados.
 */
@Data
@NoArgsConstructor
public class StarshipDetailsDto {
    private String name;
    private String model;
    private String starship_class;
    private String manufacturer;
    private String cost_in_credits;
    private String length;
    private String crew;
    private String passengers;
    private String max_atmosphering_speed;
    private String hyperdrive_rating;
    private String mglt;
    private String cargo_capacity;
    private String consumables;
    // Se obtienen las URLs en formato de Lista de películas y pilotos
    private List<String> films;
    private List<String> pilots;
}
