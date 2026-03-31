package dev.whitechoke.userService.api.dto;

import dev.whitechoke.userService.domain.db.Gender;

import java.util.Date;

public record ProfileCreateRequestDto(
        Long telegramId,
        String name,
        Date birthday,
        Short maxAge,
        Short minAge,
        Gender gender,
        Gender searchGender,
        Double latitude,
        Double longitude,
        String bio
) implements ProfileDto{
}
