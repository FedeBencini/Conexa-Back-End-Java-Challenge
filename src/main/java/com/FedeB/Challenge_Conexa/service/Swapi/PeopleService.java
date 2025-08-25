package com.FedeB.Challenge_Conexa.service.Swapi;

import com.FedeB.Challenge_Conexa.dto.People.PeopleDetailsDto;
import com.FedeB.Challenge_Conexa.integration.Requests.SwapiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Servicio para manejar operaciones relacionadas con personas/personajes (people) de SWAPI.
 * <p>
 * Este servicio utiliza un cliente SWAPI para obtener datos sobre personajes, incluyendo listas de personajes,
 * detalles por ID o búsquedas por nombre.
 */
@Service
public class PeopleService {

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
    public PeopleService(SwapiClient swapiClient) {
        this.swapiClient = swapiClient;
    }

    /**
     * Obtiene una lista paginada de todas las personas/personajes disponibles.
     * <p>
     * Este método utiliza el cliente SWAPI para recuperar una lista de personajes. Si se proporciona un número de página,
     * se obtienen los resultados correspondientes a esa página.
     *
     * @param page el número de página (opcional). Si no se proporciona, se asume la primera página.
     * @return una lista de DTOs que contienen los detalles de las personas/personajes.
     */
    public List<PeopleDetailsDto> getAllPeople(Integer page) {
        return swapiClient.getPeople(page);
    }

    /**
     * Obtiene los detalles de una persona/personaje por su ID.
     * <p>
     * Este método utiliza el cliente SWAPI para buscar un personaje específico utilizando su ID único.
     *
     * @param id el ID de la persona/personaje (requerido).
     * @return un DTO que contiene los detalles de la persona/personaje encontrada.
     */
    public PeopleDetailsDto getPeopleById(String id) {
        return swapiClient.getPersonById(id);
    }

    /**
     * Busca personas/personajes por su nombre.
     * <p>
     * Este método permite buscar personajes cuyo nombre coincida parcialmente con el término proporcionado.
     *
     * @param name el nombre de la persona/personaje (requerido).
     * @return una lista de DTOs que contienen los detalles de las personas/personajes que coinciden con el nombre buscado.
     */
    public List<PeopleDetailsDto> getPeopleByName(String name) {
        return swapiClient.getPersonByName(name);
    }
}
