package http;

import lombok.Builder;

@Builder
public record ProfileGetByFilterRequestDto(
        Double latitude,
        Double longitude,
        Short maxAge,
        Short minAge,
        Double radius,
        Gender gender
) {
}
