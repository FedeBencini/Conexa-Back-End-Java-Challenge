package com.FedeB.Challenge_Conexa.service.Swapi;

import com.FedeB.Challenge_Conexa.dto.Film.FilmDetailsDto;
import com.FedeB.Challenge_Conexa.integration.Requests.SwapiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para manejar operaciones relacionadas con películas (films) de SWAPI.
 * <p>
 * Este servicio utiliza un cliente SWAPI para obtener datos sobre películas, incluyendo listas de películas,
 * detalles por ID o búsquedas por nombre.
 */
@Service
public class FilmsService {

    private final SwapiClient swapiClient;

    /**
     * Constructor para inyectar el cliente SWAPI.
     * <p>
     * Este constructor utiliza la inyección de dependencias para proporcionar una instancia
     * del cliente SWAPI, que se utiliza para interactuar con la API de Star Wars.
     *
     * @param swapiClient el cliente SWAPI utilizado para realizar solicitudes HTTP.
     */
    @Autowired
    public FilmsService(SwapiClient swapiClient) {
        this.swapiClient = swapiClient;
    }

    /**
     * Obtiene una lista paginada de todas las películas disponibles.
     * <p>
     * Este método utiliza el cliente SWAPI para recuperar una lista de películas.
     *
     * @return una lista de DTOs que contienen los detalles de las películas.
     */
    public List<FilmDetailsDto> getAllFilms() {
        return swapiClient.getFilms();
    }

    /**
     * Obtiene los detalles de una película por su ID.
     * <p>
     * Este método utiliza el cliente SWAPI para buscar una película específica utilizando su ID único.
     *
     * @param id el ID de la película (requerido).
     * @return un DTO que contiene los detalles de la película encontrada.
     */
    public FilmDetailsDto getFilmsById(String id) {
        return swapiClient.getFilmsById(id);
    }

    /**
     * Busca ppelículas por su nombre.
     * <p>
     * Este método permite buscar películas cuyo nombre coincida parcialmente con el término proporcionado.
     *
     * @param name el nombre de la película (requerido).
     * @return una lista de DTOs que contienen los detalles de las películas que coinciden con el nombre buscado.
     */
    public List<FilmDetailsDto> getFilmsByName(String name) {
        return swapiClient.getFilmsByName(name);
    }
}
