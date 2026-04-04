package dev.whitechoke.commonLibs.http;

import dev.whitechoke.commonLibs.Gender;
import lombok.Builder;

@Builder
public record UserPreferencesResponseDto(
        Short maxAge,
        Short minAge,
        Gender searchGender,
        Double latitude,
        Double longitude,
        Double searchRadius
) {
}
