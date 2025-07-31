package mx.edu.utez.vehicleManager.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mx.edu.utez.vehicleManager.utils.Utilities;
import mx.edu.utez.vehicleManager.jwt.JwtService;
import mx.edu.utez.vehicleManager.module.user.IUserRepository;
import mx.edu.utez.vehicleManager.module.user.UserModel;

@Service
public class AuthService {

        private final IUserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthService(IUserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder,
                        AuthenticationManager authenticationManager) {
                this.userRepository = userRepository;
                this.jwtService = jwtService;
                this.passwordEncoder = passwordEncoder;
                this.authenticationManager = authenticationManager;
        }

        public ResponseEntity<Object> login(LoginRequest request) {
                try {
                        UserDetails user = userRepository.findByUsername(request.getUsername()).orElse(null);
                        if (user == null) {
                                return Utilities.authResponse(HttpStatus.UNAUTHORIZED,
                                                "Usuario no encontrado", null);
                        }

                        authenticationManager
                                        .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                                                        request.getPassword()));

                        String token = jwtService.getToken(user);
                        return Utilities.authResponse(HttpStatus.OK, "Inicio de sesión correcto", token);

                } catch (Exception e) {
                        return Utilities.authResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                                        "Ocurrió un error inesperado", null);
                }
        }

        public ResponseEntity<Object> register(RegisterRequest request) {
                try {
                        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                                return Utilities.authResponse(HttpStatus.CONFLICT,
                                                "Este usuario ya se encuentra registrado.", null);
                        }

                        UserModel user = UserModel.builder()
                                        .username(request.getUsername())
                                        .password(passwordEncoder.encode(request.getPassword()))
                                        .enabled(request.isEnabled())
                                        .employee(request.getEmployee())
                                        .role(request.getRole())
                                        .build();

                        userRepository.save(user);
                        String token = jwtService.getToken(user);
                        return Utilities.authResponse(HttpStatus.CREATED, "Usuario registrado correctamente",
                                        token);
                } catch (Exception e) {
                        return Utilities.authResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                                        "Ocurrió un error inesperado", null);
                }
        }

}
