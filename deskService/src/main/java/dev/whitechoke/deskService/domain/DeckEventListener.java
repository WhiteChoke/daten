package dev.whitechoke.deskService.domain;

import dev.whitechoke.commonLibs.kafka.ProfileCreatedEvent;
import dev.whitechoke.commonLibs.kafka.ProfileDeactivateEvent;
import dev.whitechoke.deskService.http.ProfileHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeckEventListener {

    private final ProfileHttpClient profileHttpClient;
    private final DeckCreator deckCreator;

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
        deckCreator.createDeck(event.telegramId(), ids);
    }

    @KafkaListener(
            topics = "${profile-deactivate-topic}",
            containerFactory = "profileCreatedEventListenerContainerFactory"
    )
    private void profileDeactivatedEventListener(ProfileDeactivateEvent event) {
        deckCreator.clearDeck(event.telegramId());
    }


}
