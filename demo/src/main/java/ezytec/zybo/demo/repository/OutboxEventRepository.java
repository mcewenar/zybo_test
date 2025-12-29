package ezytec.zybo.demo.repository;

import ezytec.zybo.demo.domain.OutboxEvent;
import ezytec.zybo.demo.domain.OutboxStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from OutboxEvent e where e.status = :status order by e.id asc")
    List<OutboxEvent> findAllByStatusForUpdate(@Param("status") OutboxStatus status);
}
