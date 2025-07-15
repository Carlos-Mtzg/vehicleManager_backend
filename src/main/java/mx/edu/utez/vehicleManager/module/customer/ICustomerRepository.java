package mx.edu.utez.vehicleManager.module.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<CustomerModel, Long> {

}
