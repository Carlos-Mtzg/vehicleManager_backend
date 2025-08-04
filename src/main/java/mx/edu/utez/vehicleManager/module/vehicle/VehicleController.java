package mx.edu.utez.vehicleManager.module.vehicle;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vehicle")
@CrossOrigin(origins = "*")
@Tag(name = "Vehículo", description = "Operaciones relacionadas con los vehículos")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @Operation(summary = "Obtener todos los vehículos", description = "Retorna una lista de todos los vehículos registrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @GetMapping("")
    public ResponseEntity<Object> getAllVehicles() {
        return vehicleService.getAll();
    }


    @Operation(summary = "Obtener vehículo por ID", description = "Retorna un vehículo específico por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getVehicleById(@PathVariable("id") Long id) {
        return vehicleService.getById(id);
    }


    @Operation(summary = "Crear vehículo", description = "Crea un nuevo vehículo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Vehículo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @PostMapping("")
    public ResponseEntity<Object> createVehicle(@RequestBody @Valid VehicleModel request) {
        return vehicleService.save(request);
    }


    @Operation(summary = "Actualizar vehículo", description = "Actualiza un vehículo existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehículo actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVehicle(@PathVariable("id") Long id, @RequestBody @Valid VehicleDto request) {
        return vehicleService.update(id, request);
    }


    @Operation(summary = "Eliminar vehículo", description = "Elimina un vehículo por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehículo eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVehicle(@PathVariable("id") Long id) {
        return vehicleService.delete(id);
    }


    @Operation(summary = "Contar vehículos vendidos", description = "Retorna la cantidad de vehículos vendidos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @GetMapping("/sold/count")
    public ResponseEntity<Object> getSoldVehiclesCount() {
        return vehicleService.getSoldVehiclesCount();
    }


    @Operation(summary = "Contar vehículos disponibles", description = "Retorna la cantidad de vehículos disponibles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @GetMapping("/available/count")
    public ResponseEntity<Object> getAvailableVehiclesCount() {
        return vehicleService.getAvailableVehiclesCount();
    }
}
