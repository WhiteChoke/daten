package dev.whitechoke.commonLibs.http.profileDto;

import dev.whitechoke.commonLibs.Gender;
import lombok.Builder;

@Builder
public record ProfileGetByFilterRequestDto(
        Double latitude,
        Double longitude,
        Short maxAge,
        Short minAge,
        Double searchRadius,
        Gender gender
) {
}
