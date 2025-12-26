package ezytec.zybo.demo.repository;

import ezytec.zybo.demo.domain.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayRepository extends JpaRepository<Stay, Long> {
}
