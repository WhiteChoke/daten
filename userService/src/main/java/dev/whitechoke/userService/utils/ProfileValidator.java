package dev.whitechoke.userService.utils;

import java.time.LocalDate;

import dev.whitechoke.userService.api.dto.ProfileDto;
import org.springframework.stereotype.Component;

@Component
public class ProfileValidator {

    public <T extends ProfileDto> void validate(T dto) {
        if (dto.birthday() == null) {
            throw new IllegalArgumentException("Birthday cant be null");
        }
        if (dto.birthday().isAfter(
            LocalDate.now().minusYears(16)
        )) {
            throw new IllegalAccessError("User does not meet the age requirements");
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
