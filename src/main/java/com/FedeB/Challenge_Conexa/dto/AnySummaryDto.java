package com.FedeB.Challenge_Conexa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa un resumen genérico de algunos recurso en la API de SWAPI.
 * <p>
 * Este objeto encapsula información básica común a varios recursos, como su ID único,
 * nombre y URL de acceso.
 */
@Data
@NoArgsConstructor
public class AnySummaryDto {
    private String uid;
    private String name;
    private String url;
}
