package com.FedeB.Challenge_Conexa.controller.Swapi;

import com.FedeB.Challenge_Conexa.dto.Film.FilmDetailsDto;
import com.FedeB.Challenge_Conexa.service.Swapi.FilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador para manejar operaciones relacionadas con películas (films) de SWAPI.
 * <p>
 * Este controlador proporciona endpoints para listar películas, buscar por ID o nombre,
 * y devolver detalles específicos de las películas.
 */
@RestController
@RequestMapping("/api")
public class FilmsController {

    private final FilmsService filmsService;

    /**
     * Constructor para inyectar el servicio de películas.
     *
     * @param filmsService el servicio que maneja la lógica de negocio para las películas.
     */
    @Autowired
    public FilmsController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    /**
     * Endpoint para listar todas las películas disponibles.
     * <p>
     * Este método devuelve una lista completa de todas las películas registradas en SWAPI.
     *
     * @return una respuesta HTTP con la lista de películas.
     */
    @GetMapping("/films")
    public ResponseEntity<List<FilmDetailsDto>> getFilms() {

        List<FilmDetailsDto> films = filmsService.getAllFilms();
        return ResponseEntity.ok(films);
    }

    /**
     * Endpoint para obtener los detalles de una película por su ID.
     * <p>
     * Este método busca una película específica en SWAPI utilizando su ID único.
     *
     * @param id el ID de la película (requerido).
     * @return una respuesta HTTP con los detalles de la película encontrada.
     * @throws IllegalArgumentException si no se encuentra ninguna película con el ID proporcionado.
     */
    @GetMapping("/films/id")
    public ResponseEntity<FilmDetailsDto> getFilmsById(
            @RequestParam(required = true) String id) {

        FilmDetailsDto films = filmsService.getFilmsById(id);
        return ResponseEntity.ok(films);
    }

    /**
     * Endpoint para buscar películas por su nombre.
     * <p>
     * Este método permite buscar películas cuyo nombre coincida parcialmente con el término proporcionado.
     *
     * @param name el nombre de la película (requerido).
     * @return una respuesta HTTP con la lista de películas que coinciden con el nombre buscado.
     * @throws IllegalArgumentException si no se encuentran películas con el nombre proporcionado.
     */
    @GetMapping("/films/name")
    public ResponseEntity<List<FilmDetailsDto>> getFilmsByName(
            @RequestParam(required = true) String name) {

        List<FilmDetailsDto> films = filmsService.getFilmsByName(name);
        return ResponseEntity.ok(films);
    }
}
