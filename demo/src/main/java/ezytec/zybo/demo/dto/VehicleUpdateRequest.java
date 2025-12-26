package ezytec.zybo.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleUpdateRequest(
        @NotBlank String plate,
        @NotNull Long userId
) {}
