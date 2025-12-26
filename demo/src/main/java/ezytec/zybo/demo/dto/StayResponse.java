package ezytec.zybo.demo.dto;

import java.time.LocalDateTime;

public record StayResponse(
        Long id,
        Long vehicleId,
        LocalDateTime entryTime,
        LocalDateTime exitTime,
        Integer minutes,
        Integer chargedValue,
        String status
) {}

