package com.FedeB.Challenge_Conexa.dto.Vehicle;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO que representa los detalles específicos de un vehículo en la API de SWAPI.
 * <p>
 * Este objeto contiene información relevante sobre un vehículo, como su nombre, modelo,
 * clase, fabricante, dimensiones, capacidad de carga, velocidad máxima en la atmósfera,
 * y listas de películas y pilotos asociados.
 */
@Data
@NoArgsConstructor
public class VehicleDetailsDto {
    private String name;
    private String model;
    private String vehicle_class;
    private String manufacturer;
    private String length;
    private String cost_in_credits;
    private String crew;
    private String passengers;
    private String max_atmosphering_speed;
    private String cargo_capacity;
    private String consumables;
    // Se obtienen las URLs en formato de Lista de películas y pilotos
    private List<String> films;
    private List<String> pilots;
}
