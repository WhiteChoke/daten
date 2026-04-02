package dev.whitechoke.userService.api.dto;

import dev.whitechoke.userService.domain.db.Gender;
import lombok.Builder;

@Builder
public record ProfileGetByFilterRequestDto(
        Double latitude,
        Double longitude,
        Short maxAge,
        Short minAge,
        Double radius,
        Gender gender
) {
}
