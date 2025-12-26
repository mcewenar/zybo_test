package ezytec.zybo.demo.repository;

import ezytec.zybo.demo.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
}
