package dev.whitechoke.matchService.domain.api;

import dev.whitechoke.commonLibs.http.FormResponseDto;
import dev.whitechoke.matchService.domain.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/matchs")
public class MatchController {

    private final MatchService service;

    @GetMapping("{telegramId}")
    public ResponseEntity<FormResponseDto> getMatch(
            @PathVariable Long telegramId
    ) {
        var response = service.getMatch(telegramId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
