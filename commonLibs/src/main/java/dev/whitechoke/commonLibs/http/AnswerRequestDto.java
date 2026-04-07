package dev.whitechoke.commonLibs.http;

public record AnswerRequestDto (
        Long senderId,
        Long partnerId,
        Boolean answer
) {
}
