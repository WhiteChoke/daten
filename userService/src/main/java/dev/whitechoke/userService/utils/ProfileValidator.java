package dev.whitechoke.userService.utils;

import java.time.LocalDate;

import dev.whitechoke.userService.api.dto.ProfileCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ProfileValidator {

    public void validateCreateRequest(ProfileCreateRequestDto dto) {
        if (dto.birthday() == null) {
            throw new IllegalArgumentException("The birthday cant be null");
        }
        if (dto.birthday().isAfter(
            LocalDate.now().minusYears(16)
        )) {
            throw new IllegalAccessError("The user does not meet the age requirements");
        }
        if (dto.telegramId() == null) {
            throw new IllegalArgumentException("The telegram id cant be null");
        }
        if (dto.gender() == null) {
            throw new IllegalArgumentException("The gender cant be null");
        }
        if (dto.longitude() == null) {
            throw new IllegalArgumentException("The longitude cant be null");
        }
        if (dto.latitude() == null) {
            throw new IllegalArgumentException("The latitude cant be null");
        }
        if (dto.name() == null || dto.name().isBlank()) {
            throw new IllegalArgumentException("The dev.whitechoke.commonLibs.Gender cant be empty");
        }
        if (dto.minAge() == null || dto.maxAge() == null) {
            throw new IllegalArgumentException("TThe age filter cannot be null");
        }
        if (dto.minAge() < 16 || dto.maxAge() < 16) {
            throw new IllegalArgumentException("The age filter cannot be set to less than 16 years");
        }
        if (dto.minAge() > dto.maxAge()) {
            throw new IllegalArgumentException("The minimum age cannot be greater than the maximum");
        }

    }
}
