package dev.whitechoke.commonLibs.http.profileDto;

import dev.whitechoke.commonLibs.Gender;

import java.time.Instant;
import java.time.LocalDate;

public record ProfileResponseDto(
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
        String bio,
        Double searchRadius,
        String photoLink,
        Boolean isActive
) {
}
