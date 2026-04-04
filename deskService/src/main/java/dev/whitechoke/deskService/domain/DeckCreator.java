package dev.whitechoke.deskService.domain;

import dev.whitechoke.commonLibs.kafka.ProfileCreatedEvent;
import dev.whitechoke.deskService.http.ProfileHttpClient;
import dev.whitechoke.commonLibs.http.ProfileGetByFilterRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeckCreator {

    private final ProfileHttpClient profileHttpClient;
    private final RedisTemplate<String, Long> redisTemplate;
    @Value("${redis-deck-prefix}")
    private String deckPrefix;

    @KafkaListener(
            topics = "${profile-created-topic}",
            containerFactory = "profileCreatedEventListenerContainerFactory"
    )
    private void profileCreatedEventListener(ProfileCreatedEvent event) {
        var ids = profileHttpClient.getProfileIds(
                event.latitude(),
                event.longitude(),
                event.maxAge(),
                event.minAge(),
                event.radius(),
                event.gender()
        );
        createDeck(event.telegramId(), ids);
    }

    public void updateDeck(Long telegramId) {

        var preferences = profileHttpClient.getUserPreferences(telegramId);
        var ids = profileHttpClient.getProfileIds(
                preferences.latitude(),
                preferences.longitude(),
                preferences.maxAge(),
                preferences.minAge(),
                preferences.searchRadius(),
                preferences.gender()
        );

        createDeck(telegramId, ids);
    }

    private void createDeck(
            Long telegramId,
            List<Long> ids
    ) {
        var key = deckPrefix + telegramId;
        log.info(key);
        for (var id : ids){
            redisTemplate.opsForSet().add(key, id);
        }
    }
}
