package mx.edu.utez.vehicleManager.module.vehicle;

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
@RequestMapping("/api/vehicle")
@CrossOrigin(origins = "*")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllVehicles() {
        return vehicleService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getVehicleById(@PathVariable("id") Long id) {
        return vehicleService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<Object> createVehicle(@RequestBody @Valid VehicleModel request) {
        return vehicleService.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVehicle(@PathVariable("id") Long id, @RequestBody @Valid VehicleDto request) {
        return vehicleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVehicle(@PathVariable("id") Long id) {
        return vehicleService.delete(id);
    }

    @GetMapping("/sold/count")
    public ResponseEntity<Object> getSoldVehiclesCount() {
        return vehicleService.getSoldVehiclesCount();
    }

    @GetMapping("/available/count")
    public ResponseEntity<Object> getAvailableVehiclesCount() {
        return vehicleService.getAvailableVehiclesCount();
    }
}
