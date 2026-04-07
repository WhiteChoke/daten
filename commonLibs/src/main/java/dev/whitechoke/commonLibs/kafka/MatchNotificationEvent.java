package dev.whitechoke.commonLibs.kafka;

import lombok.Builder;

@Builder
public record MatchNotificationEvent (
        Long senderId,
        Long partnerId
){
}
