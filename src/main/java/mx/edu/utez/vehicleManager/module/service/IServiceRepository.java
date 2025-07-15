package mx.edu.utez.vehicleManager.module.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceRepository extends JpaRepository<ServiceModel, Long> {

}
