package ezytec.zybo.demo.service;

import ezytec.zybo.demo.repository.OutboxEventRepository;
import ezytec.zybo.demo.repository.StayRepository;
import ezytec.zybo.demo.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StayService {
    private final VehicleRepository vehicleRepo;
    private final StayRepository stayRepo;
    private final OutboxEventRepository outboxRepo;


}
