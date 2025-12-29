package ezytec.zybo.demo.service;

import ezytec.zybo.demo.domain.OutboxEvent;
import ezytec.zybo.demo.domain.OutboxStatus;
import ezytec.zybo.demo.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    private final OutboxEventRepository repo;


    @Transactional
    public List<OutboxEvent> dispatchPending() {
        List<OutboxEvent> pending = repo.findAllByStatusForUpdate(OutboxStatus.PENDING);

        pending.forEach(e -> {
            e.setStatus(OutboxStatus.SENT);
            log.info("Dispatch event id={} type={} payload={}", e.getId(), e.getEventType(), e.getPayload());
        });

        return repo.saveAll(pending);
    }

}
