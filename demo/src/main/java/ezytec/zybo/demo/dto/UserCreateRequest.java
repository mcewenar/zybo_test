package ezytec.zybo.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserCreateRequest(
        @NotBlank String names,
        @NotBlank String document,
        @NotBlank String phone
) {}

