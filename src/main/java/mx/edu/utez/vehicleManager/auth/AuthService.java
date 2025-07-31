package mx.edu.utez.vehicleManager.auth;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.vehicleManager.utils.Utilities;
import mx.edu.utez.vehicleManager.jwt.JwtService;
import mx.edu.utez.vehicleManager.module.employee.EmployeeModel;
import mx.edu.utez.vehicleManager.module.employee.IEmployeeRepository;
import mx.edu.utez.vehicleManager.module.role.IRoleRepository;
import mx.edu.utez.vehicleManager.module.role.RoleModel;
import mx.edu.utez.vehicleManager.module.user.IUserRepository;
import mx.edu.utez.vehicleManager.module.user.UserModel;

@Service
public class AuthService {

        private final IUserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final IEmployeeRepository employeeRepository;
        private final IRoleRepository roleRepository;

        public AuthService(IUserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder,
                        AuthenticationManager authenticationManager, IEmployeeRepository employeeRepository,
                        IRoleRepository roleRepository) {
                this.userRepository = userRepository;
                this.jwtService = jwtService;
                this.passwordEncoder = passwordEncoder;
                this.authenticationManager = authenticationManager;
                this.employeeRepository = employeeRepository;
                this.roleRepository = roleRepository;
        }

        @Transactional
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

        @Transactional
        public ResponseEntity<Object> register(RegisterRequest request) {
                try {
                        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                                return Utilities.authResponse(HttpStatus.BAD_REQUEST,
                                                "El nombre de usuario ya está en uso", null);
                        }

                        if (employeeRepository.findByEmail(request.getEmail()).isPresent()) {
                                return Utilities.authResponse(HttpStatus.BAD_REQUEST,
                                                "El correo de empleado ya está en uso", null);
                        }

                        Optional<RoleModel> roleOpt = roleRepository.findById(request.getRoleId());
                        if (!roleOpt.isPresent()) {
                                return Utilities.authResponse(HttpStatus.NOT_FOUND, "Rol de usuario no encontrado",
                                                null);
                        }
                        RoleModel role = roleOpt.get();

                        EmployeeModel employee = EmployeeModel.builder()
                                        .fullName(request.getFullName())
                                        .phone(request.getPhone())
                                        .email(request.getEmail())
                                        .build();
                        employee = employeeRepository.save(employee);

                        UserModel user = UserModel.builder()
                                        .username(request.getUsername())
                                        .password(passwordEncoder.encode(request.getPassword()))
                                        .enabled(request.isEnabled())
                                        .employee(employee)
                                        .role(role)
                                        .build();
                        user = userRepository.save(user);
                        String token = jwtService.getToken(user);
                        return Utilities.authResponse(HttpStatus.CREATED, "Usuario registrado correctamente",
                                        token);
                } catch (Exception e) {
                        return Utilities.authResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                                        "Ocurrió un error inesperado", null);
                }
        }

}
