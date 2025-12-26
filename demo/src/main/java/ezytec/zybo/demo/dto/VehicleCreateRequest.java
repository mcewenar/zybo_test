package ezytec.zybo.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleCreateRequest(
        @NotBlank String plate,
        @NotNull Long userId
) {}
