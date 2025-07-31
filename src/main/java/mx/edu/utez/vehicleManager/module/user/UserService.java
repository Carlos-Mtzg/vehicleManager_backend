package mx.edu.utez.vehicleManager.module.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.vehicleManager.utils.Utilities;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
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
}
