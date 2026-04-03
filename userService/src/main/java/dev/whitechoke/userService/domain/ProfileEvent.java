package dev.whitechoke.userService.domain;

import dev.whitechoke.commonLibs.kafka.ProfileCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileEvent {

    private final KafkaTemplate<Long, ProfileCreatedEvent> kafkaTemplate;

    @Value("${profile-created-topic}")
    private String profileCreatedTopic;

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT,
            classes = ProfileCreatedEvent.class
    )
    public void sendCreatedProfileEvent(ProfileCreatedEvent event) {
        kafkaTemplate.send(
                profileCreatedTopic,
                event.telegramId(),
                event
        );
    }
}
