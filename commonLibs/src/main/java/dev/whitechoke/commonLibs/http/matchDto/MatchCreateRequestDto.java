package dev.whitechoke.commonLibs.http.matchDto;

public record MatchCreateRequestDto(
        Long senderId,
        Long partnerId,
        Boolean isLiked
) {
}
