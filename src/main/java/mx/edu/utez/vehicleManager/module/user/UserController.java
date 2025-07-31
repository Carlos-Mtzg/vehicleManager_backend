package mx.edu.utez.vehicleManager.module.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable("id") Long id,
            @RequestBody @Valid UpdateUserRequest request) {
        return userService.changeUserPassword(id, request);
    }

    @PutMapping("/disabled/{id}")
    public ResponseEntity<?> disableUser(@PathVariable("id") Long id) {
        return userService.disableUser(id);
    }

    @PutMapping("/enabled/{id}")
    public ResponseEntity<?> enableUser(@PathVariable("id") Long id) {
        return userService.enableUser(id);
    }
}
