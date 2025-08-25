package com.FedeB.Challenge_Conexa.controller.Swapi;

import com.FedeB.Challenge_Conexa.dto.People.PeopleDetailsDto;
import com.FedeB.Challenge_Conexa.dto.Starship.StarshipDetailsDto;
import com.FedeB.Challenge_Conexa.service.Swapi.PeopleService;
import com.FedeB.Challenge_Conexa.service.Swapi.StarshipsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador para manejar operaciones relacionadas con naves (starships) de SWAPI.
 * <p>
 * Este controlador proporciona endpoints para listar naves, buscar por ID o nombre,
 * y devolver detalles específicos de los naves.
 */
@RestController
@RequestMapping("/api")
public class StarshipsController {

    private final StarshipsService starshipService;

    /**
     * Constructor para inyectar el servicio de naves.
     *
     * @param starshipService el servicio que maneja la lógica de negocio para las naves.
     */
    @Autowired
    public StarshipsController(StarshipsService starshipService) {
        this.starshipService = starshipService;
    }

    /**
     * Endpoint para listar todas las naves disponibles.
     * <p>
     * Este método devuelve una lista paginada de naves registradas en SWAPI. Si no se proporciona
     * un número de página, se asume la primera página. Cada página contiene hasta 10 resultados.
     *
     * @param page el número de página (opcional).
     * @return una respuesta HTTP con la lista de naves correspondiente a la página solicitada.
     * @throws IllegalArgumentException si el número de página es inválido.
     */
    @GetMapping("/starships")
    public ResponseEntity<List<StarshipDetailsDto>> getStarships(
            @RequestParam(required = false) Integer page) {

        List<StarshipDetailsDto> people = starshipService.getAllStarships(page);
        return ResponseEntity.ok(people);
    }

    /**
     * Endpoint para obtener los detalles de una nave por su ID.
     * <p>
     * Este método busca una nave específica en SWAPI utilizando su ID único.
     *
     * @param id el ID de la nave (requerido).
     * @return una respuesta HTTP con los detalles de la nave encontrada.
     * @throws IllegalArgumentException si no se encuentra ninguna nave con el ID proporcionado.
     */
    @GetMapping("/starships/id")
    public ResponseEntity<StarshipDetailsDto> getStarshipsById(
            @RequestParam(required = true) String id) {

        StarshipDetailsDto people = starshipService.getStarshipsById(id);
        return ResponseEntity.ok(people);
    }

    /**
     * Endpoint para buscar naves por su nombre.
     * <p>
     * Este método permite buscar naves cuyo nombre coincida parcialmente con el término proporcionado.
     *
     * @param name el nombre de la nave (requerido).
     * @return una respuesta HTTP con la lista de naves que coinciden con el nombre buscado.
     * @throws IllegalArgumentException si no se encuentran naves con el nombre proporcionado.
     */
    @GetMapping("/starships/name")
    public ResponseEntity<List<StarshipDetailsDto>> getStarshipsByName(
            @RequestParam(required = true) String name) {

        List<StarshipDetailsDto> people = starshipService.getStarshipsByName(name);
        return ResponseEntity.ok(people);
    }
}
