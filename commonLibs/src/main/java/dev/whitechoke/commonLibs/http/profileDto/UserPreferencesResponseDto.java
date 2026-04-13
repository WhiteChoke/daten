package dev.whitechoke.commonLibs.http.profileDto;

import dev.whitechoke.commonLibs.Gender;
import lombok.Builder;

@Builder
public record UserPreferencesResponseDto(
        Long userId,
        Short maxAge,
        Short minAge,
        Gender searchGender,
        Double latitude,
        Double longitude,
        Double searchRadius
) {
}
