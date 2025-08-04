package mx.edu.utez.vehicleManager.module.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.edu.utez.vehicleManager.module.brand.BrandModel;

@Repository
public interface IVehicleRepository extends JpaRepository<VehicleModel, Long> {

    @Query("SELECT COUNT(v) FROM VehicleModel v WHERE v.sale_date IS NOT NULL")
    long getSoldVehiclesCount();

    @Query("SELECT COUNT(v) FROM VehicleModel v WHERE v.sale_date IS NULL")
    long getAvailableVehiclesCount();

    boolean existsByBrand(BrandModel brand);
}
