package mx.edu.utez.vehicleManager.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI vehicleManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Vehicle Manager API")
                        .description("API para la gestión de vehículos, clientes, empleados, ventas y servicios.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Equipo VehicleManager")
                                .email("soporte@vehiclemanager.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio GitHub")
                        .url("https://github.com/Carlos-Mtzg/vehicleManager_backend"));
    }
}
