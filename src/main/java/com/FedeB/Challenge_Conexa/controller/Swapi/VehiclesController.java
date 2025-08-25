package com.FedeB.Challenge_Conexa.controller.Swapi;

import com.FedeB.Challenge_Conexa.dto.Starship.StarshipDetailsDto;
import com.FedeB.Challenge_Conexa.dto.Vehicle.VehicleDetailsDto;
import com.FedeB.Challenge_Conexa.service.Swapi.StarshipsService;
import com.FedeB.Challenge_Conexa.service.Swapi.VehiclesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador para manejar operaciones relacionadas con vehículos (vehicles) de SWAPI.
 * <p>
 * Este controlador proporciona endpoints para listar vehículos, buscar por ID o nombre,
 * y devolver detalles específicos de los vehículos.
 */
@RestController
@RequestMapping("/api")
public class VehiclesController {

    private final VehiclesService vehicleService;

    /**
     * Constructor para inyectar el servicio de vehículos.
     *
     * @param vehicleService el servicio que maneja la lógica de negocio para las vehículos.
     */
    @Autowired
    public VehiclesController(VehiclesService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Endpoint para listar todas los vehículos disponibles.
     * <p>
     * Este método devuelve una lista paginada de vehículos registrados en SWAPI. Si no se proporciona
     * un número de página, se asume la primera página. Cada página contiene hasta 10 resultados.
     *
     * @param page el número de página (opcional).
     * @return una respuesta HTTP con la lista de vehículos correspondiente a la página solicitada.
     * @throws IllegalArgumentException si el número de página es inválido.
     */
    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleDetailsDto>> getVehicles(
            @RequestParam(required = false) Integer page) {

        List<VehicleDetailsDto> people = vehicleService.getAllVehicles(page);
        return ResponseEntity.ok(people);
    }

    /**
     * Endpoint para obtener los detalles de un vehículo por su ID.
     * <p>
     * Este método busca un vehículo específico en SWAPI utilizando su ID único.
     *
     * @param id el ID del vehículo (requerido).
     * @return una respuesta HTTP con los detalles del vehículo encontrada.
     * @throws IllegalArgumentException si no se encuentra ningún vehículo con el ID proporcionado.
     */
    @GetMapping("/vehicles/id")
    public ResponseEntity<VehicleDetailsDto> getVehiclesById(
            @RequestParam(required = true) String id) {

        VehicleDetailsDto people = vehicleService.getVehiclesById(id);
        return ResponseEntity.ok(people);
    }

    /**
     * Endpoint para buscar vehículos por su nombre.
     * <p>
     * Este método permite buscar vehículos cuyo nombre coincida parcialmente con el término proporcionado.
     *
     * @param name el nombre del vehículo (requerido).
     * @return una respuesta HTTP con la lista de vehículos que coinciden con el nombre buscado.
     * @throws IllegalArgumentException si no se encuentran vehículos con el nombre proporcionado.
     */
    @GetMapping("/vehicles/name")
    public ResponseEntity<List<VehicleDetailsDto>> getVehiclesByName(
            @RequestParam(required = true) String name) {

        List<VehicleDetailsDto> people = vehicleService.getVehiclesByName(name);
        return ResponseEntity.ok(people);
    }
}
