package dev.whitechoke.userService.api.dto;

import dev.whitechoke.userService.domain.db.Gender;

import java.time.Instant;
import java.util.Date;

public record ProfileCreateResponseDto(
        Long id,
        Long telegramId,
        String name,
        Date birthday,
        Short maxAge,
        Short minAge,
        Gender gender,
        Gender searchGender,
        Double latitude,
        Double longitude,
        Instant registeredAt,
        String bio
) implements ProfileDto{
}
