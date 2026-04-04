package dev.whitechoke.deskService.domain;

import dev.whitechoke.commonLibs.http.FormResponseDto;
import dev.whitechoke.deskService.http.ProfileHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final ProfileHttpClient profileHttpClient;
    private final DeckCreator deckCreator;
    private final RedisTemplate<String, Long> redisTemplate;
    @Value("${redis-deck-prefix}")
    private String deckPrefix;

    private final FormResponseDto getForm(Long telegramId) {

        var key = deckPrefix + telegramId;

        if (redisTemplate.opsForSet().size(key) < 10) {
            deckCreator.updateDeck(telegramId);
        }

        Long profileId = redisTemplate.opsForSet().pop(key);
        return profileHttpClient.getForm(profileId);
    }
}
