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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/service")
@CrossOrigin(origins = "*")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllServices() {
        return serviceService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getServiceById(@PathVariable("id") Long id) {
        return serviceService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<Object> createService(@RequestBody @Valid ServiceModel request) {
        return serviceService.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateService(@PathVariable("id") Long id, @RequestBody @Valid ServiceDto request) {
        return serviceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteService(@PathVariable("id") Long id) {
        return serviceService.delete(id);
    }
}
