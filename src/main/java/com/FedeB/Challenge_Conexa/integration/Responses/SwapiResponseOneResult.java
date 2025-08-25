package com.FedeB.Challenge_Conexa.integration.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase genérica para mapear la respuesta de la API de SWAPI cuando se obtiene un único resultado.
 * <p>
 * Esta clase se utiliza para representar la estructura de las respuestas de la API de SWAPI
 * que contienen un único resultado dentro de variable "result".
 *
 * @param <T> el tipo de elemento que representa el resultado.
 */
@Data
@AllArgsConstructor
public class SwapiResponseOneResult<T> {
    /**
     * Mensaje de estado de la respuesta.
     * <p>
     * Indica si la solicitud fue exitosa o si ocurrió algún error.
     */
    private String message;
    /**
     * Resultado obtenido de la API.
     * <p>
     * Contiene los datos principales devueltos por la API, como persona.
     */
    private T result;
}
