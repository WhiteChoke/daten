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

    public FormResponseDto getForm(Long telegramId) {

        var key = deckPrefix + telegramId;

        if (redisTemplate.opsForSet().size(key) < 10) {
            deckCreator.updateDeck(telegramId);
        }

        Object value = redisTemplate.opsForSet().pop(key);
        Long profileId = null;

        if (value instanceof Number) {
            profileId = ((Number) value).longValue();
        }
        return profileHttpClient.getForm(profileId);
    }
}
