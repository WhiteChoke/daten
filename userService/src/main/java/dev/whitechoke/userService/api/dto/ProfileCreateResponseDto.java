package dev.whitechoke.userService.api.dto;

import dev.whitechoke.userService.domain.db.Gender;

import java.time.Instant;
import java.time.LocalDate;

public record ProfileCreateResponseDto(
        Long id,
        Long telegramId,
        String name,
        LocalDate birthday,
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
