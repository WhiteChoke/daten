package dev.whitechoke.userService.utils;

import dev.whitechoke.userService.api.dto.ProfileDto;

public class ValidateProfile {

    public <T extends ProfileDto> void validate(T dto) {
        if (dto.birthday() == null) {
            throw new IllegalArgumentException("Birthday cant be null");
        }
        if (dto.telegramId() == null) {
            throw new IllegalArgumentException("Telegram id cant be null");
        }
        if (dto.gender() == null) {
            throw new IllegalArgumentException("Gender cant be null");
        }
        if (dto.longitude() == null) {
            throw new IllegalArgumentException("Longitude cant be null");
        }
        if (dto.latitude() == null) {
            throw new IllegalArgumentException("Latitude cant be null");
        }
        if (dto.name() == null || dto.name().isBlank()) {
            throw new IllegalArgumentException("Gender cant be empty");
        }

    }
}
