package mx.edu.utez.vehicleManager.module.employee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/employee")
@CrossOrigin(origins = "*")
@Tag(name = "Empleados", description = "Endpoints para gestión de empleados")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    @Operation(summary = "Listar empleados", description = "Obtiene todos los empleados registrados.")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    public ResponseEntity<Object> getAllEmployees() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener empleado por ID", description = "Obtiene un empleado específico por su ID.")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    public ResponseEntity<Object> getEmployeeById(@PathVariable("id") Long id) {
        return employeeService.getById(id);
    }

    @PostMapping("")
    @Operation(summary = "Crear empleado", description = "Crea un nuevo empleado.")
    @ApiResponse(responseCode = "201", description = "Empleado registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "409", description = "El correo ya está registrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<Object> createEmployee(@RequestBody @Valid EmployeeModel request) {
        return employeeService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar empleado", description = "Actualiza los datos de un empleado existente.")
    @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    @ApiResponse(responseCode = "409", description = "El correo ya está registrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<Object> updateEmployee(@PathVariable("id") Long id, @RequestBody @Valid EmployeeDto request) {
        return employeeService.update(id, request);
    }

}
