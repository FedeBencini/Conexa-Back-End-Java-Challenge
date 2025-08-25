package com.FedeB.Challenge_Conexa.dto.Vehicle;

import com.FedeB.Challenge_Conexa.dto.People.PeopleDetailsDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO que representa el resultado de una solicitud de vehículo desde la API de SWAPI.
 * <p>
 * Este objeto encapsula los detalles de un vehículo, incluyendo sus propiedades principales,
 * identificadores únicos y metadatos adicionales como descripción y versión.
 */
@Data
@NoArgsConstructor
public class VehicleResult {
    private VehicleDetailsDto properties;
    private String _id;
    private String description;
    private String uid;
    private String __v;
}
