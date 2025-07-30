package mx.edu.utez.vehicleManager.module.sale;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.vehicleManager.config.Utilities;
import mx.edu.utez.vehicleManager.module.customer.CustomerModel;
import mx.edu.utez.vehicleManager.module.customer.ICustomerRepository;
import mx.edu.utez.vehicleManager.module.service.IServiceRepository;
import mx.edu.utez.vehicleManager.module.service.ServiceModel;
import mx.edu.utez.vehicleManager.module.vehicle.IVehicleRepository;
import mx.edu.utez.vehicleManager.module.vehicle.VehicleModel;

@Primary
@Service
@Transactional
public class SaleService {

    private final IVehicleRepository vehicleRepository;
    private final ICustomerRepository customerRepository;
    private final IServiceRepository serviceRepository;

    public SaleService(IVehicleRepository vehicleRepository,
            ICustomerRepository customerRepository,
            IServiceRepository serviceRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
        this.serviceRepository = serviceRepository;
    }

    public ResponseEntity<Object> generateSale(SaleDto dto) {
        try {
            Optional<VehicleModel> vehicleOpt = vehicleRepository.findById(dto.getVehicleId());
            if (vehicleOpt.isEmpty()) {
                return Utilities.generateResponse(HttpStatus.NOT_FOUND, "Vehículo no encontrado", null);
            }
            VehicleModel vehicle = vehicleOpt.get();
            if (vehicle.getSale_date() != null) {
                return Utilities.generateResponse(HttpStatus.CONFLICT, "El vehículo ya fue vendido", null);
            }

            Optional<CustomerModel> customerOpt = customerRepository.findById(dto.getCustomerId());
            if (customerOpt.isEmpty()) {
                return Utilities.generateResponse(HttpStatus.NOT_FOUND, "Cliente no encontrado", null);
            }
            vehicle.setCustomer(customerOpt.get());
            vehicle.setSale_date(LocalDate.now());

            if (dto.getServiceIds() != null && !dto.getServiceIds().isEmpty()) {
                List<ServiceModel> services = dto.getServiceIds().stream()
                        .map(id -> serviceRepository.findById(id))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
                vehicle.setServices(services);
            }

            VehicleModel saved = vehicleRepository.save(vehicle);
            return Utilities.generateResponse(HttpStatus.OK, "Venta registrada exitosamente", saved);
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al generar la venta", null);
        }
    }
}
