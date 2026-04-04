package dev.whitechoke.commonLibs.kafka;

import lombok.Builder;

@Builder
public record ProfileDeactivateEvent(
        Long id,
        Long telegramId
) {
}
