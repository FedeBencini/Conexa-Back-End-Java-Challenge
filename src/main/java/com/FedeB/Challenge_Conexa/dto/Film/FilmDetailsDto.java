package com.FedeB.Challenge_Conexa.dto.Film;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Predicate;

/**
 * DTO que representa los detalles específicos de una película en la API de SWAPI.
 * <p>
 * Este objeto contiene información relevante sobre una película, como su título, episodio,
 * director, productores, fecha de lanzamiento y listas de personajes, naves y vehículos asociados.
 */
@Data
@NoArgsConstructor
public class FilmDetailsDto {
    private String title;
    private Integer episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private String release_date;
    // Se obtienen las URLs en formato de Lista de personajes, naves y vehiculos
    private List<String> characters;
    private List<String> starships;
    private List<String> vehicles;

    /**
     * Predicado estático para filtrar películas por título.
     * <p>
     * Este método permite buscar películas cuyo título contenga un término específico,
     * ignorando mayúsculas y minúsculas. Se realiza de manera manual, ya que la función
     * incorporada en la API inclue título y opening crawl en la búsqueda.
     *
     * @param name el término a buscar en el título de la película.
     * @return un predicado que evalúa si el título de la película contiene el término buscado.
     */
    public static Predicate<? super FilmDetailsDto> titleContains(String name) {
        return film -> film.getTitle().toLowerCase().contains(name.toLowerCase());
    }
}
