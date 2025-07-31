package mx.edu.utez.vehicleManager.module.user;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.vehicleManager.module.employee.EmployeeModel;
import mx.edu.utez.vehicleManager.module.employee.IEmployeeRepository;
import mx.edu.utez.vehicleManager.module.role.IRoleRepository;
import mx.edu.utez.vehicleManager.module.role.RoleModel;
import mx.edu.utez.vehicleManager.utils.Utilities;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IEmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, IRoleRepository roleRepository,
            IEmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getAllUsers() {
        List<UserModel> users = this.userRepository.findAll();
        return Utilities.generateResponse(HttpStatus.OK, "Consulta exitosa", users);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> Utilities.generateResponse(HttpStatus.OK, "Consulta exitosa", user))
                .orElseGet(() -> Utilities.generateResponse(HttpStatus.NOT_FOUND, "Usuario no encontrado", null));
    }

    @Transactional
    public ResponseEntity<?> updateUser(Long id, UpdateUserRequest dto) {
        try {
            Optional<UserModel> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty()) {
                return Utilities.generateResponse(HttpStatus.NOT_FOUND, "Usuario no encontrado", null);
            }
            UserModel user = userOpt.get();

            if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
                user.setUsername(dto.getUsername().trim());
            }

            if (dto.getEnabled() != null) {
                user.setEnabled(dto.getEnabled());
            }

            if (dto.getRoleId() != null) {
                Optional<RoleModel> roleOpt = roleRepository.findById(dto.getRoleId());
                if (roleOpt.isEmpty()) {
                    return Utilities.generateResponse(HttpStatus.NOT_FOUND, "Rol no encontrado", null);
                }
                user.setRole(roleOpt.get());
            }

            EmployeeModel employee = user.getEmployee();
            if (employee != null) {
                if (dto.getFullName() != null && !dto.getFullName().isBlank()) {
                    employee.setFullName(dto.getFullName());
                }
                if (dto.getPhone() != null && !dto.getPhone().isBlank()) {
                    employee.setPhone(dto.getPhone());
                }
                if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
                    Optional<EmployeeModel> existingEmail = employeeRepository.findByEmail(dto.getEmail().trim());
                    if (existingEmail.isPresent() && !existingEmail.get().getId().equals(employee.getId())) {
                        return Utilities.generateResponse(HttpStatus.BAD_REQUEST, "El correo ya está en uso", null);
                    }
                    employee.setEmail(dto.getEmail());
                }
                employeeRepository.save(employee);
            }

            userRepository.save(user);
            return Utilities.generateResponse(HttpStatus.OK, "Usuario actualizado correctamente", user);

        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error inesperado", null);
        }
    }

    @Transactional
    public ResponseEntity<?> changeUserPassword(Long id, UpdateUserRequest dto) {
        try {
            Optional<UserModel> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty()) {
                return Utilities.generateResponse(HttpStatus.NOT_FOUND, "Usuario no encontrado", null);
            }
            UserModel user = userOpt.get();

            if (dto.getPassword() == null || dto.getPassword().isBlank()) {
                return Utilities.generateResponse(HttpStatus.BAD_REQUEST, "La contraseña no puede estar vacía", null);
            }

            String newPasswordEncoded = passwordEncoder.encode(dto.getPassword());
            user.setPassword(newPasswordEncoded);
            userRepository.save(user);

            return Utilities.generateResponse(HttpStatus.OK, "Contraseña actualizada correctamente", user);
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error inesperado", null);
        }
    }

    @Transactional
    public ResponseEntity<?> disableUser(Long id) {
        try {
            Optional<UserModel> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty()) {
                return Utilities.generateResponse(HttpStatus.NOT_FOUND, "Usuario no encontrado", null);
            }
            UserModel user = userOpt.get();

            if (user.getEnabled() == false) {
                return Utilities.generateResponse(HttpStatus.CONFLICT, "Este usuario ya se encuentra deshabilitado",
                        null);
            }
            user.setEnabled(false);
            return Utilities.generateResponse(HttpStatus.OK, "Usuario deshabilitado correctamente", null);
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error inesperado", null);
        }
    }

    @Transactional
    public ResponseEntity<?> enableUser(Long id) {
        try {
            Optional<UserModel> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty()) {
                return Utilities.generateResponse(HttpStatus.NOT_FOUND, "Usuario no encontrado", null);
            }
            UserModel user = userOpt.get();
            if (user.getEnabled() == true) {
                return Utilities.generateResponse(HttpStatus.CONFLICT, "Este usuario ya se encuentra habilitado",
                        null);
            }

            user.setEnabled(true);
            return Utilities.generateResponse(HttpStatus.OK, "Usuario habilitado correctamente", null);
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error inesperado", null);
        }
    }
}
