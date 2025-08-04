package mx.edu.utez.vehicleManager.module.sale;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sale")
@CrossOrigin(origins = "*")
@Tag(name = "Ventas", description = "Endpoints para gestión de ventas")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("")
    @Operation(summary = "Registrar venta", description = "Registra una venta de vehículo con servicios asociados.")
    @ApiResponse(responseCode = "200", description = "Venta registrada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "404", description = "Vehículo, cliente o servicio no encontrado")
    @ApiResponse(responseCode = "409", description = "El vehículo ya fue vendido")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<Object> generateSale(@RequestBody @Valid SaleDto dto) {
        return saleService.generateSale(dto);
    }
}
