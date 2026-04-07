package dev.whitechoke.matchService.domain;

import dev.whitechoke.commonLibs.kafka.MatchNotificationEvent;
import dev.whitechoke.matchService.domain.db.MatchId;
import dev.whitechoke.matchService.domain.db.MatchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final KafkaTemplate<Long, Object> kafkaTemplate;

    @Value("${notification-match-topic}")
    private String notificationMatchTopic;

    @Transactional
    public void createMatch(Long senderId, Long partnerId, Boolean isLiked) {

        var id = new MatchId(senderId, partnerId);

        var isNew = matchRepository.upsertMatch(
                id.getFirstUserTelegramId(),
                id.getSecondUserTelegramId(),
                isLiked
        );

        if (!isNew) {
            var event = MatchNotificationEvent.builder()
                    .senderId(senderId)
                    .partnerId(partnerId)
                    .build();

            kafkaTemplate.send(
                    notificationMatchTopic,
                    senderId,
                    event);

            log.info("Users with ids {}--{} got match", senderId, partnerId);
            return;
        }
        log.info("User with id={} liked user with id={}", senderId, partnerId);
    }


}
