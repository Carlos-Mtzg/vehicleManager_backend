package mx.edu.utez.vehicleManager.module.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IVehicleRepository extends JpaRepository<VehicleModel, Long> {

}
