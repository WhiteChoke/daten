package dev.whitechoke.matchService.domain;

import dev.whitechoke.commonLibs.kafka.MatchNotificationEvent;
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
public class MatchEvent {

    private final KafkaTemplate<Long, Object> kafkaTemplate;

    @Value("${notification-match-topic}")
    private String notificationMatchTopic;

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT,
            classes = MatchNotificationEvent.class
    )
    public void sendMatchEvent(
            MatchNotificationEvent event
    ) {
        kafkaTemplate.send(
                notificationMatchTopic,
                event.senderId(),
                event);

        log.info("Users with ids {}--{} got match", event.senderId(), event.partnerId());
    }
}
