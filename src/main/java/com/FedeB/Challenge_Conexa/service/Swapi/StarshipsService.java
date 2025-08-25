package com.FedeB.Challenge_Conexa.service.Swapi;

import com.FedeB.Challenge_Conexa.dto.Starship.StarshipDetailsDto;
import com.FedeB.Challenge_Conexa.integration.Requests.SwapiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para manejar operaciones relacionadas con naves (people) de SWAPI.
 * <p>
 * Este servicio utiliza un cliente SWAPI para obtener datos sobre naves, incluyendo listas de naves,
 * detalles por ID o búsquedas por nombre.
 */
@Service
public class StarshipsService {

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
    public StarshipsService(SwapiClient swapiClient) {
        this.swapiClient = swapiClient;
    }

    /**
     * Obtiene una lista paginada de todas las naves disponibles.
     * <p>
     * Este método utiliza el cliente SWAPI para recuperar una lista de naves. Si se proporciona un número de página,
     * se obtienen los resultados correspondientes a esa página.
     *
     * @param page el número de página (opcional). Si no se proporciona, se asume la primera página.
     * @return una lista de DTOs que contienen los detalles de las naves.
     */
    public List<StarshipDetailsDto> getAllStarships(Integer page) {
        return swapiClient.getStarships(page);
    }

    /**
     * Obtiene los detalles de una nave por su ID.
     * <p>
     * Este método utiliza el cliente SWAPI para buscar una nave específica utilizando su ID único.
     *
     * @param id el ID de la nave (requerido).
     * @return un DTO que contiene los detalles de la nave encontrada.
     */
    public StarshipDetailsDto getStarshipsById(String id) {
        return swapiClient.getStarshipsById(id);
    }

    /**
     * Busca naves por su nombre.
     * <p>
     * Este método permite buscar naves cuyo nombre coincida parcialmente con el término proporcionado.
     *
     * @param name el nombre de la nave (requerido).
     * @return una lista de DTOs que contienen los detalles de las naves que coinciden con el nombre buscado.
     */
    public List<StarshipDetailsDto> getStarshipsByName(String name) {
        return swapiClient.getStarshipsByName(name);
    }
}
