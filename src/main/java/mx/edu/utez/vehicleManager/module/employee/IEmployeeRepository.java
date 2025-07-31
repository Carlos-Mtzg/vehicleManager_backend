package mx.edu.utez.vehicleManager.module.employee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<EmployeeModel, Long> {

    Optional<EmployeeModel> findByEmail(String email);

}
