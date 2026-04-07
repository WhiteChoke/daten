package dev.whitechoke.matchService.domain.api;

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

    @GetMapping
    public ResponseEntity<Void> getMatch(
            @RequestParam Long senderId,
            @RequestParam Long partnerId,
            @RequestParam Boolean isLiked
    ) {
        service.createMatch(senderId, partnerId, isLiked);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
