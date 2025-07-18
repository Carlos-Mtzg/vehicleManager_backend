package mx.edu.utez.vehicleManager.module.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceRepository extends JpaRepository<ServiceModel, Long> {

    boolean existsByCode(String code);
}
