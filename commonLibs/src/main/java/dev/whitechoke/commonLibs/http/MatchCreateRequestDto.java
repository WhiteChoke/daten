package dev.whitechoke.commonLibs.http;

public record MatchCreateRequestDto(
        Long senderId,
        Long partnerId,
        Boolean isLiked
) {
}
