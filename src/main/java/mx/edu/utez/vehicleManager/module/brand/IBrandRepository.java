package mx.edu.utez.vehicleManager.module.brand;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBrandRepository extends JpaRepository<BrandModel, Long> {

    Optional<BrandModel> findByName(String name);
}
