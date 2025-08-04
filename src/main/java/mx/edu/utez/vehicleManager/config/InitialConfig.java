package mx.edu.utez.vehicleManager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import mx.edu.utez.vehicleManager.module.employee.EmployeeModel;
import mx.edu.utez.vehicleManager.module.employee.IEmployeeRepository;
import mx.edu.utez.vehicleManager.module.role.IRoleRepository;
import mx.edu.utez.vehicleManager.module.role.RoleModel;
import mx.edu.utez.vehicleManager.module.user.IUserRepository;
import mx.edu.utez.vehicleManager.module.user.UserModel;

@Component
public class InitialConfig implements CommandLineRunner {

    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final IEmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.user}")
    private String userAdmin;

    @Value("${admin.password}")
    private String passwordAdmin;

    public InitialConfig(IRoleRepository roleRepository, IUserRepository userRepository,
            IEmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findAll().isEmpty()) {
            RoleModel adminRole = new RoleModel();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);

            RoleModel employeeRole = new RoleModel();
            employeeRole.setName("EMPLOYEE");
            roleRepository.save(employeeRole);
        }

        if (userRepository.findByUsername(userAdmin).isEmpty()) {
            EmployeeModel adminEmployee = EmployeeModel.builder()
                    .fullName("Administrador")
                    .phone("7771234567")
                    .email("admin@mail.com.mx")
                    .build();
            adminEmployee = employeeRepository.save(adminEmployee);

            RoleModel adminRole = roleRepository.findAll()
                    .stream()
                    .filter(role -> "ADMIN".equals(role.getName()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));

            UserModel adminUser = UserModel.builder()
                    .username(userAdmin.toUpperCase().trim())
                    .password(passwordEncoder.encode(passwordAdmin.trim()))
                    .enabled(true)
                    .employee(adminEmployee)
                    .role(adminRole)
                    .build();
            adminUser = userRepository.save(adminUser);
        }
    }

}
