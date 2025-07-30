package mx.edu.utez.vehicleManager.module.vehicle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.vehicleManager.config.Utilities;
import mx.edu.utez.vehicleManager.module.brand.BrandModel;
import mx.edu.utez.vehicleManager.module.brand.IBrandRepository;

@Service
@Primary
@Transactional
public class VehicleService {

    private final IVehicleRepository vehicleRepository;
    private final IBrandRepository brandRepository;

    public VehicleService(IVehicleRepository vehicleRepository, IBrandRepository brandRepository) {
        this.vehicleRepository = vehicleRepository;
        this.brandRepository = brandRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getAll() {
        List<VehicleModel> vehicles = this.vehicleRepository.findAll();
        return Utilities.generateResponse(HttpStatus.OK, "Consulta exitosa", vehicles);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getById(Long id) {
        return vehicleRepository.findById(id)
                .map(vehicle -> Utilities.generateResponse(HttpStatus.OK, "Consulta exitosa", vehicle))
                .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Empleado no encontrado", null));
    }

    @Transactional
    public ResponseEntity<Object> save(VehicleModel vehicle) {
        try {
            Optional<BrandModel> brandOpt = brandRepository.findById(vehicle.getBrand().getId());
            if (!brandOpt.isPresent()) {
                return Utilities.generateResponse(HttpStatus.NOT_FOUND, "No se encontró la marca", null);
            }
            vehicle.setBrand(brandOpt.get());
            vehicle.setRegistration_date(LocalDate.now());
            VehicleModel saved = vehicleRepository.save(vehicle);
            return Utilities.generateResponse(HttpStatus.CREATED, "Vehículo registrado correctamente", saved);
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al guardar el registro", null);
        }
    }

    @Transactional
    public ResponseEntity<Object> update(Long id, VehicleDto dto) {
        try {
            return vehicleRepository.findById(id)
                    .map(existingVehicle -> {
                        if (dto.getModel() != null) {
                            existingVehicle.setModel(dto.getModel());
                        }
                        if (dto.getColor() != null) {
                            existingVehicle.setColor(dto.getColor());
                        }
                        if (dto.getPrice() != null) {
                            existingVehicle.setPrice(dto.getPrice());
                        }
                        VehicleModel updated = vehicleRepository.save(existingVehicle);
                        return Utilities.generateResponse(HttpStatus.OK, "Vehículo actualizado exitosamente", updated);
                    })
                    .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Vehículo no encontrado", null));
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el vehículo",
                    null);
        }
    }

    @Transactional
    public ResponseEntity<Object> delete(Long id) {
        try {
            return vehicleRepository.findById(id).map(vehicle -> {
                this.vehicleRepository.delete(vehicle);
                return Utilities.generateResponse(HttpStatus.OK, "Vehículo eliminado exitosamente", id);
            }).orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Vehículo no encontrado", null));
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al eliminar el registro",
                    null);
        }
    }
}
