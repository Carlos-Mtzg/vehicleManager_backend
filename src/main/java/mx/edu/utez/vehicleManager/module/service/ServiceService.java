package mx.edu.utez.vehicleManager.module.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.vehicleManager.utils.Utilities;

@Service
@Primary
@Transactional
public class ServiceService {

    private final IServiceRepository repository;

    public ServiceService(IServiceRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getAll() {
        List<ServiceModel> services = this.repository.findAll();
        return Utilities.generateResponse(HttpStatus.OK, "Consulta exitosa", services);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getById(Long id) {
        return repository.findById(id)
                .map(service -> Utilities.generateResponse(HttpStatus.OK, "Consulta exitosa", service))
                .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Servicio no encontrado", null));
    }

    @Transactional
    public ResponseEntity<Object> save(ServiceModel service) {
        if (repository.existsByCode(service.getCode())) {
            return Utilities.generateResponse(HttpStatus.CONFLICT, "El código del servicio ya está registrado",
                    null);
        }
        try {
            ServiceModel savedService = this.repository.save(service);
            return Utilities.generateResponse(HttpStatus.CREATED, "Servicio guardado exitosamente", savedService);
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al guardar el registro", null);
        }
    }

    @Transactional
    public ResponseEntity<Object> update(Long id, ServiceDto dto) {
        try {
            return repository.findById(id)
                    .map(existingService -> {
                        if (dto.getName() != null) {
                            existingService.setName(dto.getName());
                        }
                        if (dto.getDescription() != null) {
                            existingService.setDescription(dto.getDescription());
                        }
                        if (dto.getPrice() != null) {
                            existingService.setPrice(dto.getPrice());
                        }
                        ServiceModel updated = repository.save(existingService);
                        return Utilities.generateResponse(HttpStatus.OK, "Servicio actualizado exitosamente", updated);
                    })
                    .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Servicio no encontrado", null));
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el servicio",
                    null);
        }
    }

    @Transactional
    public ResponseEntity<Object> delete(Long id) {
        try {
            return repository.findById(id).map(service -> {
                if (service.getVehicles() != null && !service.getVehicles().isEmpty()) {
                    return Utilities.generateResponse(HttpStatus.CONFLICT,
                            "No se puede eliminar el servicio porque está asignado a uno o más vehículos", null);
                }
                this.repository.delete(service);
                return Utilities.generateResponse(HttpStatus.OK, "Servicio eliminado exitosamente", id);
            })
                    .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Servicio no encontrado", null));
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al eliminar el registro",
                    null);
        }
    }

}
