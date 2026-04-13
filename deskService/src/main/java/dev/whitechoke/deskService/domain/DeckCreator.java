package dev.whitechoke.deskService.domain;

import dev.whitechoke.deskService.http.ProfileHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeckCreator {

    private final ProfileHttpClient profileHttpClient;
    private final RedisTemplate<String, Long> redisTemplate;
    @Value("${redis-deck-prefix}")
    private String deckPrefix;
    private final int ttl = 12;

    public void updateDeck(Long telegramId) {

        var preferences = profileHttpClient.getUserPreferences(telegramId);
        var ids = profileHttpClient.getProfileIds(
                preferences.latitude(),
                preferences.longitude(),
                preferences.maxAge(),
                preferences.minAge(),
                preferences.searchRadius(),
                preferences.searchGender()
        );

        ids.remove(preferences.userId());

        var key = createDeck(telegramId, ids);
        log.info("Updated deck with id={}", key);
    }

    public String createDeck(
            Long telegramId,
            List<Long> ids
    ) {
        var key = deckPrefix + telegramId;
        for (var id : ids){
            redisTemplate.opsForSet().add(key, id);
            redisTemplate.expire(key, ttl, TimeUnit.HOURS);
        }

        return key;
    }

    public void clearDeck(Long telegramId) {
        var key = deckPrefix + telegramId;
        redisTemplate.delete(key);
        log.info("The deck of the user with id={} was cleared", telegramId);
    }
}
