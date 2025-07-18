package mx.edu.utez.vehicleManager.module.employee;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.vehicleManager.config.Utilities;
import mx.edu.utez.vehicleManager.module.role.IRoleRepository;
import mx.edu.utez.vehicleManager.module.role.RoleModel;

@Service
@Primary
@Transactional
public class EmployeeService {

    private final IEmployeeRepository employeeRepository;
    private final IRoleRepository roleRepository;

    public EmployeeService(IEmployeeRepository employeeRepository, IRoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getAll() {
        List<EmployeeModel> employees = this.employeeRepository.findAll();
        return Utilities.generateResponse(HttpStatus.OK, "Consulta exitosa", employees);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getById(Long id) {
        return employeeRepository.findById(id)
                .map(employee -> Utilities.generateResponse(HttpStatus.OK, "Consulta exitosa", employee))
                .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Empleado no encontrado", null));
    }

    @Transactional
    public ResponseEntity<Object> save(EmployeeModel employee) {
        try {
            if (employee.getRole() != null && employee.getRole().getId() != null) {
                RoleModel role = roleRepository.findById(employee.getRole().getId()).orElse(null);
                employee.setRole(role);
            }
            EmployeeModel savedEmployee = this.employeeRepository.save(employee);
            return Utilities.generateResponse(HttpStatus.CREATED, "Empleado registrado exitosamente", savedEmployee);
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al guardar el registro", null);
        }
    }

    @Transactional
    public ResponseEntity<Object> update(Long id, EmployeeDto dto) {
        try {
            return employeeRepository.findById(id)
                    .map(existingEmployee -> {
                        if (dto.getFull_name() != null) {
                            existingEmployee.setFull_name(dto.getFull_name());
                        }
                        if (dto.getPhone() != null) {
                            existingEmployee.setPhone(dto.getPhone());
                        }
                        if (dto.getEmail() != null) {
                            existingEmployee.setEmail(dto.getEmail());
                        }
                        if (dto.getRoleId() != null) {
                            RoleModel role = roleRepository.findById(dto.getRoleId())
                                    .orElse(null);
                            if (role != null) {
                                existingEmployee.setRole(role);
                            } else {
                                return Utilities.generateResponse(HttpStatus.NOT_FOUND,
                                        "Rol no encontrado", null);
                            }
                        }
                        EmployeeModel updated = employeeRepository.save(existingEmployee);
                        return Utilities.generateResponse(HttpStatus.OK, "Empleado actualizado exitosamente", updated);
                    })
                    .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Empleado no encontrado", null));
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el empleado",
                    null);
        }
    }

    @Transactional
    public ResponseEntity<Object> delete(Long id) {
        try {
            return employeeRepository.findById(id).map(employee -> {
                this.employeeRepository.delete(employee);
                return Utilities.generateResponse(HttpStatus.OK, "Empleado eliminado exitosamente", id);
            }).orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Empleado no encontrado", null));
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al eliminar el registro",
                    null);
        }
    }
}
