package mx.edu.utez.vehicleManager.module.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.vehicleManager.config.Utilities;
import mx.edu.utez.vehicleManager.module.employee.EmployeeModel;
import mx.edu.utez.vehicleManager.module.employee.IEmployeeRepository;

@Service
@Primary
@Transactional
public class CustomerService {

    public final ICustomerRepository customerRepository;
    public final IEmployeeRepository employeeRepository;

    public CustomerService(ICustomerRepository customerRepository, IEmployeeRepository employeeRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getAll() {
        List<CustomerModel> customers = this.customerRepository.findAll();
        return Utilities.generateResponse(HttpStatus.OK, "Consulta exitosa", customers);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getById(Long id) {
        return customerRepository.findById(id)
                .map(customer -> Utilities.generateResponse(HttpStatus.OK, "Consulta exitosa", customer))
                .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Cliente no encontrado", null));

    }

    @Transactional
    public ResponseEntity<Object> save(CustomerModel customer) {
        try {
            if (customer.getEmployee() == null || customer.getEmployee().getId() == null) {
                return Utilities.generateResponse(HttpStatus.BAD_REQUEST, "Debes asignar un empleado válido al cliente",
                        null);
            }

            if (customer.getEmail() != null && customerRepository.findByEmail(customer.getEmail()).isPresent()) {
                return Utilities.generateResponse(HttpStatus.CONFLICT,
                        "Ya hay un cliente registrado con este correo electrónico",
                        null);
            }

            if (customer.getPhone() != null && customerRepository.findByPhone(customer.getPhone()).isPresent()) {
                return Utilities.generateResponse(HttpStatus.CONFLICT, "Ya hay un cliente registrado con este teléfono",
                        null);
            }

            Optional<EmployeeModel> employeeOpt = employeeRepository.findById(customer.getEmployee().getId());
            if (!employeeOpt.isPresent()) {
                return Utilities.generateResponse(HttpStatus.NOT_FOUND, "Empleado no encontrado", null);
            }
            customer.setEmployee(employeeOpt.get());

            CustomerModel savedCustomer = this.customerRepository.save(customer);
            return Utilities.generateResponse(HttpStatus.CREATED, "Cliente registrado exitosamente", savedCustomer);
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al guardar el registro", null);
        }
    }

    @Transactional
    public ResponseEntity<Object> update(Long id, CustomerDto dto) {
        try {
            return customerRepository.findById(id)
                    .map(existingCustomer -> {
                        if (dto.getFull_name() != null) {
                            existingCustomer.setFull_name(dto.getFull_name());
                        }
                        if (dto.getPhone() != null) {
                            if (customerRepository.findByPhone(dto.getPhone())
                                    .filter(c -> !c.getId().equals(id)).isPresent()) {
                                return Utilities.generateResponse(HttpStatus.CONFLICT,
                                        "Ya hay un cliente registrado con este teléfono", null);
                            }
                            existingCustomer.setPhone(dto.getPhone());
                        }
                        if (dto.getEmail() != null) {
                            if (customerRepository.findByEmail(dto.getEmail())
                                    .filter(c -> !c.getId().equals(id)).isPresent()) {
                                return Utilities.generateResponse(HttpStatus.CONFLICT,
                                        "Ya hay un cliente registrado con este correo electrónico", null);
                            }
                            existingCustomer.setEmail(dto.getEmail());
                        }
                        if (dto.getEmployeeId() != null) {
                            Optional<EmployeeModel> employeeOpt = employeeRepository.findById(dto.getEmployeeId());
                            if (employeeOpt.isPresent()) {
                                existingCustomer.setEmployee(employeeOpt.get());
                            } else {
                                return Utilities.generateResponse(HttpStatus.NOT_FOUND, "Empleado no econtrado", null);
                            }

                        }
                        CustomerModel updated = customerRepository.save(existingCustomer);
                        return Utilities.generateResponse(HttpStatus.OK, "Cliente actualizado correctamente", updated);
                    })
                    .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Cliente no encontrado", null));
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el servicio",
                    null);
        }
    }

    @Transactional
    public ResponseEntity<Object> delete(Long id) {
        try {
            return customerRepository.findById(id).map(customer -> {
                if (customer.getVehicles() != null && !customer.getVehicles().isEmpty()) {
                    return Utilities.generateResponse(HttpStatus.CONFLICT,
                            "No puedes eliminar un cliente con vehículos asignados", null);
                }
                this.customerRepository.delete(customer);
                return Utilities.generateResponse(HttpStatus.OK, "Cliente eliminado correctamente", null);
            })
                    .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Cliente no encontrado", null));
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al eliminar el registro",
                    null);
        }
    }
}
