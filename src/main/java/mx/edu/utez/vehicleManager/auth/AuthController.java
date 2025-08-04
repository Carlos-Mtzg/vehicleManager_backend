package mx.edu.utez.vehicleManager.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticación", description = "Operaciones de autenticación y registro de usuarios")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @Operation(summary = "Iniciar sesión", description = "Autentica a un usuario y retorna un token JWT si las credenciales son válidas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
        @ApiResponse(responseCode = "400", description = "Credenciales inválidas o datos incorrectos"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }


    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema. Solo permitido para administradores.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario ya existe"),
        @ApiResponse(responseCode = "403", description = "Prohibido. Solo administradores pueden registrar usuarios."),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest request) {
        return authService.register(request);
    }

}
