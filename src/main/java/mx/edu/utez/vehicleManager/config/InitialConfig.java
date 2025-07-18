package mx.edu.utez.vehicleManager.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import mx.edu.utez.vehicleManager.module.role.IRoleRepository;
import mx.edu.utez.vehicleManager.module.role.RoleModel;

@Component
public class InitialConfig implements CommandLineRunner {

    private final IRoleRepository roleRepository;

    public InitialConfig(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
    }

}
