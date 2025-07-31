package mx.edu.utez.vehicleManager.module.brand;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.vehicleManager.module.vehicle.IVehicleRepository;
import mx.edu.utez.vehicleManager.utils.Utilities;

@Service
@Primary
@Transactional
public class BrandService {

    private final IBrandRepository repository;
    private final IVehicleRepository vehicleRepository;

    public BrandService(IBrandRepository repository, IVehicleRepository vehicleRepository) {
        this.repository = repository;
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getAll() {
        List<BrandModel> brands = this.repository.findAll(Sort.by("name").descending());
        return Utilities.generateResponse(HttpStatus.OK, "Consulta exitosa", brands);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getById(Long id) {
        return repository.findById(id)
                .map(brand -> Utilities.generateResponse(HttpStatus.OK, "Consulta exitosa", brand))
                .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Marca no encontrada", null));
    }

    @Transactional
    public ResponseEntity<Object> save(BrandModel brand) {
        try {
            String upperName = brand.getName().toUpperCase();
            Optional<BrandModel> brandOpt = repository.findByName(upperName);
            if (brandOpt.isPresent()) {
                return Utilities.generateResponse(HttpStatus.CONFLICT, "Esta marca ya se encuentra registrada", null);
            }
            brand.setName(upperName);
            BrandModel saveBrand = this.repository.save(brand);
            return Utilities.generateResponse(HttpStatus.CREATED, "Marca guardada exitosamente", saveBrand);
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al guardar el registro", null);
        }
    }

    @Transactional
    public ResponseEntity<Object> update(Long id, BrandModel brand) {
        try {
            return repository.findById(id)
                    .map(existingBrand -> {
                        existingBrand.setName(brand.getName());
                        BrandModel updatedBrand = this.repository.save(existingBrand);
                        return Utilities.generateResponse(HttpStatus.OK, "Marca actualizada exitosamente",
                                updatedBrand);
                    })
                    .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Marca no encontrada", null));
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al actualizar el registro",
                    null);
        }
    }

    @Transactional
    public ResponseEntity<Object> delete(Long id) {
        try {
            return repository.findById(id)
                    .map(brand -> {
                        if (vehicleRepository.existsByBrand(brand)) {
                            return Utilities.generateResponse(HttpStatus.CONFLICT,
                                    "No se puede eliminar la marca porque tiene vehículos asignados", id);
                        }
                        this.repository.delete(brand);
                        return Utilities.generateResponse(HttpStatus.OK, "Marca eliminada exitosamente", null);
                    })
                    .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Marca no encontrada", null));
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al eliminar el registro",
                    null);
        }
    }

}
