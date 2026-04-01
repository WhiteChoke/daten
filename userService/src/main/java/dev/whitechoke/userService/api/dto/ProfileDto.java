package dev.whitechoke.userService.api.dto;

import dev.whitechoke.userService.domain.db.Gender;

import java.time.LocalDate;

public interface ProfileDto {
    Long telegramId();
    String name();
    LocalDate birthday();
    Gender gender();
    Double latitude();
    Double longitude();
}
