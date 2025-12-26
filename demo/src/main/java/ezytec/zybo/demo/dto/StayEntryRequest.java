package ezytec.zybo.demo.dto;

import jakarta.validation.constraints.NotNull;

public record StayEntryRequest(@NotNull Long vehicleId) {}
