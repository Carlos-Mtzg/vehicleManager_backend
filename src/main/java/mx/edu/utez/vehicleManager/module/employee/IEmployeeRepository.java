package mx.edu.utez.vehicleManager.module.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<EmployeeModel, Long> {

}
