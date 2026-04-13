package dev.whitechoke.commonLibs.http.matchDto;

public record AnswerRequestDto (
        Long senderId,
        Long partnerId,
        Boolean answer
) {
}
