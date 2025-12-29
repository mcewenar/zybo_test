package ezytec.zybo.demo.service;

import ezytec.zybo.demo.domain.*;
import ezytec.zybo.demo.exception.CustomExceptions;
import ezytec.zybo.demo.repository.OutboxEventRepository;
import ezytec.zybo.demo.repository.StayRepository;
import ezytec.zybo.demo.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class StayService {
    private final VehicleRepository vehicleRepo;
    private final StayRepository stayRepo;
    private final OutboxEventRepository outboxRepo;

    private static final int RATE_PER_MINUTE = 100;

    //Avoid starvation or concurrency problem
    // 1) Two simultaneous entries for same vehicle => only one creates OPEN stay
    @Transactional
    public Stay registerEntry(Long vehicleId) {
        Vehicle v = vehicleRepo.findByIdForUpdate(vehicleId)
                .orElseThrow(() -> new CustomExceptions.NotFoundException("Vehicle not found"));

        boolean alreadyOpen = stayRepo.existsByVehicleIdAndStatus(vehicleId, StayStatus.OPEN);
        if (alreadyOpen) {
            throw new CustomExceptions.ConflictException("Vehicle already has an OPEN stay");
        }

        Stay s = new Stay();
        s.setVehicle(v);
        s.setEntryTime(LocalDateTime.now());
        s.setStatus(StayStatus.OPEN);

        return stayRepo.save(s);
    }

    // 2) Two simultaneous exits for same stay => only one closes
    @Transactional
    public Stay registerExit(Long stayId) {
        Stay s = stayRepo.findByIdForUpdate(stayId)
                .orElseThrow(() -> new CustomExceptions.NotFoundException("Stay not found"));

        if (s.getStatus() == StayStatus.CLOSED) {
            throw new CustomExceptions.ConflictException("Stay is already CLOSED");
        }

        LocalDateTime exit = LocalDateTime.now();
        s.setExitTime(exit);

        long seconds = Duration.between(s.getEntryTime(), exit).getSeconds();
        int minutes = (int) Math.ceil(seconds / 60.0);     // rounding rule
        if (minutes < 0) minutes = 0; // defensive

        int charged = minutes * RATE_PER_MINUTE;

        s.setMinutesTotal(minutes);
        s.setChargedValue(charged);
        s.setStatus(StayStatus.CLOSED);

        Stay saved = stayRepo.save(s);

        OutboxEvent e = new OutboxEvent();
        e.setEventType("SALIDA_REGISTRADA");
        e.setStatus(OutboxStatus.PENDING);
        e.setCreatedAt(LocalDateTime.now());
        e.setPayload(toJsonPayload(saved.getId(), saved.getVehicle().getId(), charged, exit));

        outboxRepo.save(e);

        return saved;
    }

    private String toJsonPayload(Long stayId, Long vehicleId, int charged, LocalDateTime exitTime) {
        return """
      {"stayId":%d,"vehicleId":%d,"chargedValue":%d,"exitTime":"%s"}
      """.formatted(stayId, vehicleId, charged, exitTime);
    }
}
