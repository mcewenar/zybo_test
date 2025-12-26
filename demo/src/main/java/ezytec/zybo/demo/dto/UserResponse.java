package ezytec.zybo.demo.dto;

import lombok.Builder;

@Builder
public record UserResponse(Long id, String names, String document, String phone) {}
