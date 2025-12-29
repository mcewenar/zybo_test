package ezytec.zybo.demo.repository;

import ezytec.zybo.demo.domain.Stay;
import ezytec.zybo.demo.domain.StayStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StayRepository extends JpaRepository<Stay, Long> {

    boolean existsByVehicleIdAndStatus(Long vehicleId, StayStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stay s where s.id = :id")
    Optional<Stay> findByIdForUpdate(@Param("id") Long id);
}
