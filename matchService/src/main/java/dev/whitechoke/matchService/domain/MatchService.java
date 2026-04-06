package dev.whitechoke.matchService.domain;

import dev.whitechoke.commonLibs.http.FormResponseDto;
import dev.whitechoke.matchService.domain.db.MatchEntity;
import dev.whitechoke.matchService.domain.db.MatchRepository;
import dev.whitechoke.matchService.http.DeckHttpClient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final DeckHttpClient deckHttpClient;
    private final MatchRepository matchRepository;


    public FormResponseDto getMatch(Long telegramId) {

        var form = deckHttpClient.getForm(telegramId);

        saveMatch(telegramId, form.telegramId());

        return form;
    }

    @Transactional
    private void saveMatch(Long firstTgId, Long secondTgId) {
        var match = new MatchEntity();
        match.setIds(firstTgId, secondTgId);
        matchRepository.save(match);
    }
}
