package mx.edu.utez.vehicleManager.module.brand;

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
@RequestMapping("/api/brand")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllBrands() {
        return brandService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBrandById(@PathVariable("id") Long id) {
        return brandService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<Object> createBrand(@RequestBody @Valid BrandModel request) {
        return brandService.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBrand(@PathVariable("id") Long id, @RequestBody @Valid BrandModel request) {
        return brandService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBrand(@PathVariable("id") Long id) {
        return brandService.delete(id);
    }
}
