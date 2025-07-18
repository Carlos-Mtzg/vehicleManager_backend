package mx.edu.utez.vehicleManager.module.employee;

import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllEmployees() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable("id") Long id) {
        return employeeService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<Object> createEmployee(@RequestBody @Valid EmployeeModel request) {
        return employeeService.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable("id") Long id, @RequestBody @Valid EmployeeDto request) {
        return employeeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("id") Long id) {
        return employeeService.delete(id);
    }

}
