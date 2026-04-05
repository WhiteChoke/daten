package dev.whitechoke.matchService.http;

import dev.whitechoke.commonLibs.http.FormResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(
        accept = "application/json",
        contentType = "application/json",
        url = "/api/v1/decks"
)
public interface DeckHttpClient {
    @GetExchange(url = "{telegramId}")
    FormResponseDto getForm(@PathVariable Long telegramId);
}
