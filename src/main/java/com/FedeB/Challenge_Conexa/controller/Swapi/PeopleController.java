package com.FedeB.Challenge_Conexa.controller.Swapi;

import com.FedeB.Challenge_Conexa.dto.People.PeopleDetailsDto;
import com.FedeB.Challenge_Conexa.service.Swapi.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador para manejar operaciones relacionadas con personas/personajes (people) de SWAPI.
 * <p>
 * Este controlador proporciona endpoints para listar personas, buscar por ID o nombre,
 * y devolver detalles específicos de los personas.
 */
@RestController
@RequestMapping("/api")
public class PeopleController {

    private final PeopleService peopleService;

    /**
     * Constructor para inyectar el servicio de personas.
     *
     * @param peopleService el servicio que maneja la lógica de negocio para las personas.
     */
    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    /**
     * Endpoint para listar todas las películas disponibles.
     * <p>
     * Este método devuelve una lista paginada de personas registradas en SWAPI. Si no se proporciona
     * un número de página, se asume la primera página. Cada página contiene hasta 10 resultados.
     *
     * @param page el número de página (opcional).
     * @return una respuesta HTTP con la lista de personas correspondiente a la página solicitada.
     * @throws IllegalArgumentException si el número de página es inválido.
     */
    @GetMapping("/people")
    public ResponseEntity<List<PeopleDetailsDto>> getPeople(
            @RequestParam(required = false) Integer page) {

        List<PeopleDetailsDto> people = peopleService.getAllPeople(page);
        return ResponseEntity.ok(people);
    }

    /**
     * Endpoint para obtener los detalles de una persona por su ID.
     * <p>
     * Este método busca una persona específica en SWAPI utilizando su ID único.
     *
     * @param id el ID de la persona (requerido).
     * @return una respuesta HTTP con los detalles de la persona encontrada.
     * @throws IllegalArgumentException si no se encuentra ninguna persona con el ID proporcionado.
     */
    @GetMapping("/people/id")
    public ResponseEntity<PeopleDetailsDto> getPeopleById(
            @RequestParam(required = true) String id) {

        PeopleDetailsDto people = peopleService.getPeopleById(id);
        return ResponseEntity.ok(people);
    }

    /**
     * Endpoint para buscar personas por su nombre.
     * <p>
     * Este método permite buscar personas cuyo nombre coincida parcialmente con el término proporcionado.
     *
     * @param name el nombre de la persona (requerido).
     * @return una respuesta HTTP con la lista de personas que coinciden con el nombre buscado.
     * @throws IllegalArgumentException si no se encuentran personas con el nombre proporcionado.
     */
    @GetMapping("/people/name")
    public ResponseEntity<List<PeopleDetailsDto>> getPeopleByName(
            @RequestParam(required = true) String name) {

        List<PeopleDetailsDto> people = peopleService.getPeopleByName(name);
        return ResponseEntity.ok(people);
    }
}
