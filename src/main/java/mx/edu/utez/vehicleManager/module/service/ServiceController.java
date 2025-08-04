package mx.edu.utez.vehicleManager.module.service;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/service")
@CrossOrigin(origins = "*")
@Tag(name = "Servicios", description = "Endpoints para gestión de servicios")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("")
    @Operation(summary = "Listar servicios", description = "Obtiene todos los servicios registrados.")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    public ResponseEntity<Object> getAllServices() {
        return serviceService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener servicio por ID", description = "Obtiene un servicio específico por su ID.")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    public ResponseEntity<Object> getServiceById(@PathVariable("id") Long id) {
        return serviceService.getById(id);
    }

    @PostMapping("")
    @Operation(summary = "Crear servicio", description = "Crea un nuevo servicio.")
    @ApiResponse(responseCode = "201", description = "Servicio guardado exitosamente")
    @ApiResponse(responseCode = "409", description = "El código del servicio ya está registrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<Object> createService(@RequestBody @Valid ServiceModel request) {
        return serviceService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar servicio", description = "Actualiza los datos de un servicio existente.")
    @ApiResponse(responseCode = "200", description = "Servicio actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<Object> updateService(@PathVariable("id") Long id, @RequestBody @Valid ServiceDto request) {
        return serviceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar servicio", description = "Elimina un servicio si no está asignado a vehículos.")
    @ApiResponse(responseCode = "200", description = "Servicio eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    @ApiResponse(responseCode = "409", description = "No se puede eliminar el servicio porque está asignado a uno o más vehículos")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<Object> deleteService(@PathVariable("id") Long id) {
        return serviceService.delete(id);
    }
}
