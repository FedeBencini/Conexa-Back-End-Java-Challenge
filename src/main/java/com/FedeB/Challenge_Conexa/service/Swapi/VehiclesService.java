package com.FedeB.Challenge_Conexa.service.Swapi;

import com.FedeB.Challenge_Conexa.dto.Vehicle.VehicleDetailsDto;
import com.FedeB.Challenge_Conexa.integration.Requests.SwapiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para manejar operaciones relacionadas con vehículos (vehicles) de SWAPI.
 * <p>
 * Este servicio utiliza un cliente SWAPI para obtener datos sobre vehículos, incluyendo listas de vehículos,
 * detalles por ID o búsquedas por nombre.
 */
@Service
public class VehiclesService {

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
    public VehiclesService(SwapiClient swapiClient) {
        this.swapiClient = swapiClient;
    }

    /**
     * Obtiene una lista paginada de todas los vehículos disponibles.
     * <p>
     * Este método utiliza el cliente SWAPI para recuperar una lista de vehículos. Si se proporciona un número de página,
     * se obtienen los resultados correspondientes a esa página.
     *
     * @param page el número de página (opcional). Si no se proporciona, se asume la primera página.
     * @return una lista de DTOs que contienen los detalles de los vehículos.
     */
    public List<VehicleDetailsDto> getAllVehicles(Integer page) {
        return swapiClient.getVehicles(page);
    }

    /**
     * Obtiene los detalles de un vehículo por su ID.
     * <p>
     * Este método utiliza el cliente SWAPI para buscar un vehículo específico utilizando su ID único.
     *
     * @param id el ID del vehículo (requerido).
     * @return un DTO que contiene los detalles del vehículo encontrada.
     */
    public VehicleDetailsDto getVehiclesById(String id) {
        return swapiClient.getVehiclesById(id);
    }

    /**
     * Busca vehículos por su nombre.
     * <p>
     * Este método permite buscar vehículos cuyo nombre coincida parcialmente con el término proporcionado.
     *
     * @param name el nombre del vehículo (requerido).
     * @return una lista de DTOs que contienen los detalles de los vehículos que coinciden con el nombre buscado.
     */
    public List<VehicleDetailsDto> getVehiclesByName(String name) {
        return swapiClient.getVehiclesByName(name);
    }
}
