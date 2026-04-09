package dev.whitechoke.matchService.api;

import dev.whitechoke.commonLibs.http.AnswerRequestDto;
import dev.whitechoke.commonLibs.http.MatchCreateRequestDto;
import dev.whitechoke.matchService.domain.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/matchs")
public class MatchController {

    private final MatchService service;

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody MatchCreateRequestDto request
    ) {
        service.createMatch(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/answer")
    public ResponseEntity<Void> setAnswer(
            @RequestBody AnswerRequestDto request
    ) {
        service.setAnswer(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
