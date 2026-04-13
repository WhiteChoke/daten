package dev.whitechoke.userService.api.dto;

import dev.whitechoke.commonLibs.Gender;

import java.time.LocalDate;

public record ProfileCreateRequestDto(
        Long telegramId,
        String name,
        LocalDate birthday,
        Short maxAge,
        Short minAge,
        Gender gender,
        Gender searchGender,
        Double latitude,
        Double longitude,
        String bio,
        Double searchRadius,
        String photoLink
) {
}
