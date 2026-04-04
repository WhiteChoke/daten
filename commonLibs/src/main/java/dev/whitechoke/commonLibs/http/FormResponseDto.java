package dev.whitechoke.commonLibs.http;

import dev.whitechoke.commonLibs.Gender;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FormResponseDto(
        Long id,
        Long telegramId,
        String name,
        LocalDate birthday,
        Gender gender,
        String bio
) {
}
