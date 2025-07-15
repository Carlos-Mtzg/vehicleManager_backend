package mx.edu.utez.vehicleManager.module.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBrandRepository extends JpaRepository<BrandModel, Long> {

}
