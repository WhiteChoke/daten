package dev.whitechoke.commonLibs.http;

import java.time.LocalDateTime;

public record ErrorResponseDto (
        String message,
        String detailMessage,
        LocalDateTime errorTime
) {
}
