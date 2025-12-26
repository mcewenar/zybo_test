package ezytec.zybo.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserUpdateRequest(
        @NotBlank String names,
        @NotBlank String phone
) {}
