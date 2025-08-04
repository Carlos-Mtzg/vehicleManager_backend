package mx.edu.utez.vehicleManager.module.brand;

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
@RequestMapping("/api/brand")
@CrossOrigin(origins = "*")
@Tag(name = "Marcas", description = "Endpoints para gestión de marcas de vehículos")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("")
    @Operation(summary = "Listar marcas", description = "Obtiene todas las marcas registradas.")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    public ResponseEntity<Object> getAllBrands() {
        return brandService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener marca por ID", description = "Obtiene una marca específica por su ID.")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    @ApiResponse(responseCode = "404", description = "Marca no encontrada")
    public ResponseEntity<Object> getBrandById(@PathVariable("id") Long id) {
        return brandService.getById(id);
    }

    @PostMapping("")
    @Operation(summary = "Crear marca", description = "Crea una nueva marca de vehículo.")
    @ApiResponse(responseCode = "201", description = "Marca guardada exitosamente")
    @ApiResponse(responseCode = "409", description = "La marca ya existe")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<Object> createBrand(@RequestBody @Valid BrandModel request) {
        return brandService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar marca", description = "Actualiza los datos de una marca existente.")
    @ApiResponse(responseCode = "200", description = "Marca actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "Marca no encontrada")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<Object> updateBrand(@PathVariable("id") Long id, @RequestBody @Valid BrandModel request) {
        return brandService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar marca", description = "Elimina una marca si no tiene vehículos asignados.")
    @ApiResponse(responseCode = "200", description = "Marca eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "Marca no encontrada")
    @ApiResponse(responseCode = "409", description = "No se puede eliminar la marca porque tiene vehículos asignados")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<Object> deleteBrand(@PathVariable("id") Long id) {
        return brandService.delete(id);
    }
}
