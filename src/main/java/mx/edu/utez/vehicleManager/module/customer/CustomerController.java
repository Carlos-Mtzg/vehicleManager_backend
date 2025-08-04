package mx.edu.utez.vehicleManager.module.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
@Tag(name = "Clientes", description = "Endpoints para gestión de clientes")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    @Operation(summary = "Listar clientes", description = "Obtiene todos los clientes registrados.")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    public ResponseEntity<Object> getAllCustomers() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Obtiene un cliente específico por su ID.")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<Object> getCustomerById(@PathVariable("id") Long id) {
        return customerService.getById(id);
    }

    @PostMapping("")
    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente.")
    @ApiResponse(responseCode = "201", description = "Cliente registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    @ApiResponse(responseCode = "409", description = "El correo o teléfono ya está registrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid CustomerModel request) {
        return customerService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente.")
    @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "404", description = "Cliente o empleado no encontrado")
    @ApiResponse(responseCode = "409", description = "El correo o teléfono ya está registrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<Object> updateCustomer(@PathVariable("id") Long id, @RequestBody @Valid CustomerDto request) {
        return customerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente si no tiene vehículos asignados.")
    @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    @ApiResponse(responseCode = "409", description = "No puedes eliminar un cliente con vehículos asignados")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("id") Long id) {
        return customerService.delete(id);
    }
}
