package com.FedeB.Challenge_Conexa.integration.Requests;

import com.FedeB.Challenge_Conexa.dto.Film.FilmDetailsDto;
import com.FedeB.Challenge_Conexa.dto.Film.FilmResult;
import com.FedeB.Challenge_Conexa.dto.People.PeopleDetailsDto;
import com.FedeB.Challenge_Conexa.dto.People.PeopleResult;
import com.FedeB.Challenge_Conexa.dto.AnySummaryDto;
import com.FedeB.Challenge_Conexa.dto.Starship.StarshipDetailsDto;
import com.FedeB.Challenge_Conexa.dto.Starship.StarshipResult;
import com.FedeB.Challenge_Conexa.dto.Vehicle.VehicleDetailsDto;
import com.FedeB.Challenge_Conexa.dto.Vehicle.VehicleResult;
import com.FedeB.Challenge_Conexa.integration.Responses.SwapiResponseOneResult;
import com.FedeB.Challenge_Conexa.integration.Responses.SwapiResponseMultResult;
import com.FedeB.Challenge_Conexa.integration.Responses.SwapiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Cliente para realizar llamadas HTTP a la API de SWAPI (Star Wars API).
 * <p>
 * Esta clase encapsula las operaciones necesarias para interactuar con la API de SWAPI,
 * incluyendo la obtención de datos sobre personajes, películas, naves espaciales y vehículos.
 * Utiliza {@link RestTemplate} para realizar las solicitudes HTTP y maneja la conversión
 * de las respuestas a objetos DTO específicos.
 */
@Component
@Slf4j
public class SwapiClient {

    private static final String BASE_URL = "https://www.swapi.tech/api/";
    private final RestTemplate restTemplate;

    /**
     * Constructor para inyectar el {@link RestTemplate}.
     * <p>
     * Este constructor utiliza la inyección de dependencias para proporcionar una instancia
     * de {@link RestTemplate}, que se utiliza para realizar las solicitudes HTTP a la API de SWAPI.
     *
     * @param restTemplate el cliente HTTP utilizado para realizar las solicitudes.
     */
    @Autowired
    public SwapiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*-------------------*/
    /* PERSONAS (PEOPLE) */
    /*-------------------*/
    /**
     * Obtiene una lista paginada de todas las personas/personajes disponibles.
     * <p>
     * Este método realiza una solicitud GET a la API de SWAPI para obtener una lista de personas.
     * Si se proporciona un número de página, se obtienen los resultados correspondientes a esa página.
     * De lo contrario, se asume la primera página.
     *
     * @param page el número de página (opcional). Si no se proporciona, se asume la primera página.
     * @return una lista de DTOs que contienen los detalles de las personas/personajes.
     * @throws RuntimeException si ocurre un error durante la solicitud o la respuesta es nula.
     */
    public List<PeopleDetailsDto> getPeople(Integer page) {
        String url = BASE_URL + "people";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

        if (page != null) {
            builder.queryParam("page", page);
        }
        // 'limit' con valor predeterminado 10 para evitar grandes cantidades de datos y permitir buscar por página
        // (sin "limit", no funciona parámetro "page" en llamada a SWAPI)
        int limit = 10;
        builder.queryParam("limit", limit);

        log.info("Buscando persona con parámetro: page={}", page);
        ResponseEntity<SwapiResponses<AnySummaryDto>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SwapiResponses<AnySummaryDto>>() {}
        );

        // Validar si se obtuvo error en la respuesta
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error en la obtención de data desde SWAPI: " + response.getStatusCode());
        }

        // Validar que la respuesta no sea nula
        if (response.getBody() == null || response.getBody().getResults() == null) {
            throw new RuntimeException("Falló la obtención de data desde SWAPI: Cuerpo de respuesta es null.");
        }

        // Por cada persona obtenida, se almacena su Id
        List<String> ids = response.getBody().getResults().stream()
                .map(AnySummaryDto::getUid)
                .toList();

        // Se devuelve la lista de personas, obtenendo sus detalles por Id
        return ids.stream()
                .map(this::getPersonById)
                .toList();
    }

    /**
     * Obtiene los detalles de una persona/personaje por su ID.
     * <p>
     * Este método realiza una solicitud GET a la API de SWAPI para buscar una persona específica
     * utilizando su ID único.
     *
     * @param id el ID de la persona/personaje (requerido).
     * @return un DTO que contiene los detalles de la persona/personaje encontrada.
     * @throws RuntimeException si ocurre un error durante la solicitud o no se encuentra la persona.
     */
    public PeopleDetailsDto getPersonById(String id) {
        log.info("Buscando persona por ID: {}", id);
        String url = BASE_URL + "people/" + id;

        ResponseEntity<SwapiResponseOneResult<PeopleResult>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SwapiResponseOneResult<PeopleResult>>() {}
        );

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error buscando persona por ID: " + response.getStatusCode());
        }

        if (response.getBody() == null || response.getBody().getResult() == null) {
            throw new RuntimeException("No se encontró persona por ID: " + id);
        }

        return response.getBody().getResult().getProperties();
    }

    /**
     * Busca personas/personajes por su nombre.
     * <p>
     * Este método permite buscar personas cuyo nombre coincida parcialmente con el término proporcionado.
     *
     * @param name el nombre de la persona/personaje (requerido).
     * @return una lista de DTOs que contienen los detalles de las personas/personajes que coinciden con el nombre buscado.
     * @throws RuntimeException si ocurre un error durante la solicitud o no se encuentran coincidencias.
     */
    public List<PeopleDetailsDto> getPersonByName(String name) {
        log.info("Buscando personas por nombre: {}", name);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL + "people")
                .queryParam("name", name);

        ResponseEntity<SwapiResponseMultResult<PeopleResult>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SwapiResponseMultResult<PeopleResult>>() {}
        );

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error buscando persona por nombre: " + response.getStatusCode());
        }

        if (response.getBody() == null || response.getBody().getResult() == null) {
            throw new RuntimeException("No se encontró persona por nombre: " + name);
        }

        return response.getBody().getResult().stream()
                .map(PeopleResult::getProperties)
                .toList();
    }


    /*-------------------*/
    /* PELÍCULAS (FILMS) */
    /*-------------------*/
    /**
     * Obtiene una lista de todas las películas disponibles.
     * <p>
     * Este método realiza una solicitud GET a la API de SWAPI para obtener una lista de películas.
     *
     * @return una lista de DTOs que contienen los detalles de las películas.
     * @throws RuntimeException si ocurre un error durante la solicitud o la respuesta es nula.
     */
    public List<FilmDetailsDto> getFilms() {
        String url = BASE_URL + "films";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);


        log.info("Buscando películas");
        ResponseEntity<SwapiResponseMultResult<FilmResult>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SwapiResponseMultResult<FilmResult>>() {}
        );

        // Validar si se obtuvo error en la respuesta
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error en la obtención de data desde SWAPI: " + response.getStatusCode());
        }

        // Validar que la respuesta no sea nula
        if (response.getBody() == null || response.getBody().getResult() == null) {
            throw new RuntimeException("Falló la obtención de data desde SWAPI: Cuerpo de respuesta es null.");
        }

        // Se devuelve la lista de películas
        return response.getBody().getResult().stream()
                .map(FilmResult::getProperties)
                .toList();
    }

    /**
     * Obtiene los detalles de una película por su ID.
     * <p>
     * Este método realiza una solicitud GET a la API de SWAPI para buscar una película específica
     * utilizando su ID único.
     *
     * @param id el ID de la película (requerido).
     * @return un DTO que contiene los detalles de la película encontrada.
     * @throws RuntimeException si ocurre un error durante la solicitud o no se encuentra la película.
     */
    public FilmDetailsDto getFilmsById(String id) {
        log.info("Buscando película por ID: {}", id);
        String url = BASE_URL + "films/" + id;

        ResponseEntity<SwapiResponseOneResult<FilmResult>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SwapiResponseOneResult<FilmResult>>() {}
        );

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error buscando pelicula por ID: " + response.getStatusCode());
        }

        if (response.getBody() == null || response.getBody().getResult() == null) {
            throw new RuntimeException("No se encontró pelicula por ID: " + id);
        }

        return response.getBody().getResult().getProperties();
    }

    /**
     * Busca películas por su nombre.
     * <p>
     * Este método permite buscar películas cuyo nombre coincida parcialmente con el término proporcionado.
     *
     * @param name el nombre de la película (requerido).
     * @return una lista de DTOs que contienen los detalles de las películas que coinciden con el nombre buscado.
     * @throws RuntimeException si ocurre un error durante la solicitud o no se encuentran coincidencias.
     */
    public List<FilmDetailsDto> getFilmsByName(String name) {
        log.info("Buscando películas por nombre: {}", name);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL + "films")
                .queryParam("name", name);

        ResponseEntity<SwapiResponseMultResult<FilmResult>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SwapiResponseMultResult<FilmResult>>() {}
        );

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error buscando película por nombre: " + response.getStatusCode());
        }

        if (response.getBody() == null || response.getBody().getResult() == null) {
            throw new RuntimeException("No se encontró película por nombre: " + name);
        }

        return response.getBody().getResult().stream()
                .map(FilmResult::getProperties)
                .filter(FilmDetailsDto.titleContains(name))
                .toList();
    }

    /*-------------------*/
    /* NAVES (STARSHIPS) */
    /*-------------------*/
    /**
     * Obtiene una lista paginada de todas las naves disponibles.
     * <p>
     * Este método realiza una solicitud GET a la API de SWAPI para obtener una lista de naves.
     * Si se proporciona un número de página, se obtienen los resultados correspondientes a esa página.
     * De lo contrario, se asume la primera página.
     *
     * @param page el número de página (opcional). Si no se proporciona, se asume la primera página.
     * @return una lista de DTOs que contienen los detalles de las naves.
     * @throws RuntimeException si ocurre un error durante la solicitud o la respuesta es nula.
     */
    public List<StarshipDetailsDto> getStarships(Integer page) {
        String url = BASE_URL + "starships";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

        if (page != null) {
            builder.queryParam("page", page);
        }
        // 'limit' con valor predeterminado 10 para evitar grandes cantidades de datos y permitir buscar por página
        // (sin "limit", no funciona parámetro "page" en llamada a SWAPI)
        int limit = 10;
        builder.queryParam("limit", limit);

        log.info("Buscando naves con parámetro: page={}", page);
        ResponseEntity<SwapiResponses<AnySummaryDto>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SwapiResponses<AnySummaryDto>>() {}
        );

        // Validar si se obtuvo error en la respuesta
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error en la obtención de data desde SWAPI: " + response.getStatusCode());
        }

        // Validar que la respuesta no sea nula
        if (response.getBody() == null || response.getBody().getResults() == null) {
            throw new RuntimeException("Falló la obtención de data desde SWAPI: Cuerpo de respuesta es null.");
        }

        // Por cada nave obtenida, se almacena su Id
        List<String> ids = response.getBody().getResults().stream()
                .map(AnySummaryDto::getUid)
                .toList();

        // Se devuelve la lista de naves, obtenendo sus detalles por Id
        return ids.stream()
                .map(this::getStarshipsById)
                .toList();
    }

    /**
     * Obtiene los detalles de una nave por su ID.
     * <p>
     * Este método realiza una solicitud GET a la API de SWAPI para buscar una nave específica
     * utilizando su ID único.
     *
     * @param id el ID de la nave (requerido).
     * @return un DTO que contiene los detalles de la nave encontrada.
     * @throws RuntimeException si ocurre un error durante la solicitud o no se encuentra la nave.
     */
    public StarshipDetailsDto getStarshipsById(String id) {
        log.info("Buscando nave por ID: {}", id);
        String url = BASE_URL + "starships/" + id;

        ResponseEntity<SwapiResponseOneResult<StarshipResult>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SwapiResponseOneResult<StarshipResult>>() {}
        );

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error buscando nave por ID: " + response.getStatusCode());
        }

        if (response.getBody() == null || response.getBody().getResult() == null) {
            throw new RuntimeException("No se encontró nave por ID: " + id);
        }

        return response.getBody().getResult().getProperties();
    }

    /**
     * Busca naves por su nombre.
     * <p>
     * Este método permite buscar naves cuyo nombre coincida parcialmente con el término proporcionado.
     *
     * @param name el nombre de la nave (requerido).
     * @return una lista de DTOs que contienen los detalles de las naves que coinciden con el nombre buscado.
     * @throws RuntimeException si ocurre un error durante la solicitud o no se encuentran coincidencias.
     */
    public List<StarshipDetailsDto> getStarshipsByName(String name) {
        log.info("Buscando naves por nombre: {}", name);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL + "starships")
                .queryParam("name", name);

        ResponseEntity<SwapiResponseMultResult<StarshipResult>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SwapiResponseMultResult<StarshipResult>>() {}
        );

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error buscando nave por nombre: " + response.getStatusCode());
        }

        if (response.getBody() == null || response.getBody().getResult() == null) {
            throw new RuntimeException("No se encontró nave por nombre: " + name);
        }

        return response.getBody().getResult().stream()
                .map(StarshipResult::getProperties)
                .toList();
    }

    /*----------------------*/
    /* VEHÍCULOS (VEHICLES) */
    /*----------------------*/
    /**
     * Obtiene una lista paginada de todas los vehículos disponibles.
     * <p>
     * Este método realiza una solicitud GET a la API de SWAPI para obtener una lista de vehículos.
     * Si se proporciona un número de página, se obtienen los resultados correspondientes a esa página.
     * De lo contrario, se asume la primera página.
     *
     * @param page el número de página (opcional). Si no se proporciona, se asume la primera página.
     * @return una lista de DTOs que contienen los detalles de los vehículos.
     * @throws RuntimeException si ocurre un error durante la solicitud o la respuesta es nula.
     */
    public List<VehicleDetailsDto> getVehicles(Integer page) {
        String url = BASE_URL + "vehicles";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

        if (page != null) {
            builder.queryParam("page", page);
        }
        // 'limit' con valor predeterminado 10 para evitar grandes cantidades de datos y permitir buscar por página
        // (sin "limit", no funciona parámetro "page" en llamada a SWAPI)
        int limit = 10;
        builder.queryParam("limit", limit);

        log.info("Buscando vehículos con parámetro: page={}", page);
        ResponseEntity<SwapiResponses<AnySummaryDto>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SwapiResponses<AnySummaryDto>>() {}
        );

        // Validar si se obtuvo error en la respuesta
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error en la obtención de data desde SWAPI: " + response.getStatusCode());
        }

        // Validar que la respuesta no sea nula
        if (response.getBody() == null || response.getBody().getResults() == null) {
            throw new RuntimeException("Falló la obtención de data desde SWAPI: Cuerpo de respuesta es null.");
        }

        // Por cada vehículo obtenido, se almacena su Id
        List<String> ids = response.getBody().getResults().stream()
                .map(AnySummaryDto::getUid)
                .toList();

        // Se devuelve la lista de vehículos, obtenendo sus detalles por Id
        return ids.stream()
                .map(this::getVehiclesById)
                .toList();
    }

    /**
     * Obtiene los detalles de un vehículo por su ID.
     * <p>
     * Este método realiza una solicitud GET a la API de SWAPI para buscar un vehículo específica
     * utilizando su ID único.
     *
     * @param id el ID del vehículo (requerido).
     * @return un DTO que contiene los detalles del vehículo encontrada.
     * @throws RuntimeException si ocurre un error durante la solicitud o no se encuentra el vehículo.
     */
    public VehicleDetailsDto getVehiclesById(String id) {
        log.info("Buscando vehículo por ID: {}", id);
        String url = BASE_URL + "vehicles/" + id;

        ResponseEntity<SwapiResponseOneResult<VehicleResult>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SwapiResponseOneResult<VehicleResult>>() {}
        );

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error buscando vehículo por ID: " + response.getStatusCode());
        }

        if (response.getBody() == null || response.getBody().getResult() == null) {
            throw new RuntimeException("No se encontró vehículo por ID: " + id);
        }

        return response.getBody().getResult().getProperties();
    }

    /**
     * Busca vehículos por su nombre.
     * <p>
     * Este método permite buscar vehículos cuyo nombre coincida parcialmente con el término proporcionado.
     *
     * @param name el nombre del vehículo (requerido).
     * @return una lista de DTOs que contienen los detalles de los vehículos que coinciden con el nombre buscado.
     * @throws RuntimeException si ocurre un error durante la solicitud o no se encuentran coincidencias.
     */
    public List<VehicleDetailsDto> getVehiclesByName(String name) {
        log.info("Buscando vehículos por nombre: {}", name);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL + "vehicles")
                .queryParam("name", name);

        ResponseEntity<SwapiResponseMultResult<VehicleResult>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SwapiResponseMultResult<VehicleResult>>() {}
        );

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error buscando vehículo por nombre: " + response.getStatusCode());
        }

        if (response.getBody() == null || response.getBody().getResult() == null) {
            throw new RuntimeException("No se encontró vehículo por nombre: " + name);
        }

        return response.getBody().getResult().stream()
                .map(VehicleResult::getProperties)
                .toList();
    }
}
