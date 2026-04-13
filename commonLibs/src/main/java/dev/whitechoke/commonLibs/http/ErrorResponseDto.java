package dev.whitechoke.commonLibs.http;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponseDto (
        String message,
        String detailMessage,
        LocalDateTime errorTime
) {
}
