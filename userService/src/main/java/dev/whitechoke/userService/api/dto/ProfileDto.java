package dev.whitechoke.userService.api.dto;

import dev.whitechoke.userService.domain.db.Gender;

import java.util.Date;

public interface ProfileDto {
    Long telegramId();
    String name();
    Date birthday();
    Gender gender();
    Double latitude();
    Double longitude();
}
