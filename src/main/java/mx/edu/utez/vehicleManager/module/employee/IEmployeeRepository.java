package mx.edu.utez.vehicleManager.module.employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<EmployeeModel, Long> {

}
