package com.FedeB.Challenge_Conexa.integration.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Clase genérica para mapear la respuesta de la API de SWAPI cuando se obtienen múltiples resultados.
 * <p>
 * Esta clase se utiliza para representar la estructura de las respuestas de la API de SWAPI
 * que contienen una lista de resultados dentro de variable "result".
 *
 * @param <T> el tipo de los elementos contenidos en la lista de resultados.
 */
@Data
@AllArgsConstructor
public class SwapiResponseMultResult<T> {
    /**
     * Mensaje de estado de la respuesta.
     * <p>
     * Indica si la solicitud fue exitosa o si ocurrió algún error.
     */
    private String message;
    /**
     * Lista de resultados obtenidos de la API.
     * <p>
     * Contiene los datos principales devueltos por la API, como personas.
     */
    private List<T> result;
}
