package mx.edu.utez.vehicleManager.module.sale;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/sale")
@CrossOrigin(origins = "*")
@Tag(name = "Venta", description = "Operaciones relacionadas con las ventas de vehículos")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }


    @Operation(summary = "Generar venta", description = "Registra una nueva venta de vehículo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Venta registrada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @PostMapping("")
    public ResponseEntity<Object> generateSale(@RequestBody @Valid SaleDto dto) {
        return saleService.generateSale(dto);
    }
}
