package dev.whitechoke.deskService.api;

import dev.whitechoke.commonLibs.http.FormResponseDto;
import dev.whitechoke.deskService.domain.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/decks")
public class DeckController {

    private final DeckService service;

    @GetMapping("{telegramId}")
    public ResponseEntity<FormResponseDto> getForm(
            @PathVariable Long telegramId
    ) {

        var response = service.getForm(telegramId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
