package ezytec.zybo.demo.repository;

import ezytec.zybo.demo.domain.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent,Long> {
}
