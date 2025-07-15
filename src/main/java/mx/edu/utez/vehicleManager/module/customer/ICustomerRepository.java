package mx.edu.utez.vehicleManager.module.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<CustomerModel, Long> {

}
