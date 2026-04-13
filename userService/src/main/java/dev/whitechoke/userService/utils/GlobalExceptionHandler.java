package dev.whitechoke.userService.utils;

import dev.whitechoke.commonLibs.http.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(
            Exception ex
    ) {
        log.error("Handle exception\n", ex);

        var message = ErrorResponseDto.builder()
                .message("Internal Server Error")
                .detailMessage(ex.getMessage())
                .errorTime(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(message);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(
            EntityNotFoundException ex
    ) {

        log.error("Handle entity not found exception\n", ex);

        var message = ErrorResponseDto.builder()
                .message("Entity not found")
                .detailMessage(ex.getMessage())
                .errorTime(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequest(Exception ex) {

        log.error("Handle bad request exception\n", ex);

        var message = ErrorResponseDto.builder()
                .message("Bad request")
                .detailMessage(ex.getMessage())
                .errorTime(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }
}
