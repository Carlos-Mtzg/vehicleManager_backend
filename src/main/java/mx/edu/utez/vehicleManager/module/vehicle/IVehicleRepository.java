package mx.edu.utez.vehicleManager.module.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVehicleRepository extends JpaRepository<VehicleModel, Long> {

}
