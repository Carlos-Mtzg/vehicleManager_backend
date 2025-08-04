package mx.edu.utez.vehicleManager.config;

import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(info = @Info(title = "Vehicle Manager API", description = "API RESTful para la gestión integral de vehículos, usuarios, empleados, clientes, ventas y servicios. Proporciona endpoints seguros para la administración de inventario, control de usuarios y operaciones de negocio en una agencia automotriz. Incluye autenticación JWT, control de roles y protección avanzada de datos.", version = "1.0.0"), security = {
        @SecurityRequirement(name = "Security Token") })
@SecurityScheme(name = "Security Token", description = "Access Token For My API", type = SecuritySchemeType.HTTP, paramName = HttpHeaders.AUTHORIZATION, in = SecuritySchemeIn.HEADER, scheme = "bearer", bearerFormat = "JWT")
public class SwaggerConfig {
}
