package dev.whitechoke.userService.domain;

import dev.whitechoke.commonLibs.kafka.ProfileCreatedEvent;
import dev.whitechoke.commonLibs.kafka.ProfileDeactivateEvent;
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

    private final KafkaTemplate<Long, Object> kafkaTemplate;

    @Value("${profile-created-topic}")
    private String profileCreatedTopic;
    @Value("${profile-deactivate-topic}")
    private String profileDeactivateTopic;

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
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT,
            classes = ProfileDeactivateEvent.class
    )
    public void sendCreatedProfileEvent(ProfileDeactivateEvent event) {
        kafkaTemplate.send(
                profileDeactivateTopic,
                event.telegramId(),
                event
        );
    }
}
