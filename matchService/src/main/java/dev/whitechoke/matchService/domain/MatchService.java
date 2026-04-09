package dev.whitechoke.matchService.domain;

import dev.whitechoke.commonLibs.http.AnswerRequestDto;
import dev.whitechoke.commonLibs.http.MatchCreateRequestDto;
import dev.whitechoke.commonLibs.kafka.MatchNotificationEvent;
import dev.whitechoke.matchService.domain.db.MatchId;
import dev.whitechoke.matchService.domain.db.MatchRepository;

import jakarta.persistence.EntityNotFoundException;
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
    @Value("${notification-like-topic}")
    private String notificationLikeTopic;

    @Transactional
    public void createMatch(MatchCreateRequestDto request) {

        var id = new MatchId(
                request.senderId(),
                request.partnerId()
        );

        var entity = matchRepository.upsertMatch(
                id.getFirstUserTelegramId(),
                id.getSecondUserTelegramId(),
                request.isLiked()
        );

        if (entity.getSecondAnswer() != null) {
            if (entity.getFirstAnswer()
                    .equals(entity.getSecondAnswer())
            ) {
                sendMatchEvent(
                        request.senderId(),
                        request.partnerId(),
                        notificationMatchTopic
                );
            }
            return;
        }
        log.info("User with id={} liked user with id={}", request.senderId(), request.partnerId());
    }

    @Transactional
    public void setAnswer(AnswerRequestDto request) {
        var id = new MatchId(
                request.senderId(),
                request.partnerId()
        );
        var match = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found match with id=" + id));

        if (match.getFirstAnswer()
                .equals(match.getSecondAnswer())
        ) {
            sendMatchEvent(
                    request.senderId(),
                    request.partnerId(),
                    notificationLikeTopic
            );
        }
    }

    private void sendMatchEvent(
            Long senderId,
            Long partnerId,
            String topic
    ) {
        var event = MatchNotificationEvent.builder()
                .senderId(senderId)
                .partnerId(partnerId)
                .build();

        kafkaTemplate.send(
                topic,
                senderId,
                event);

        log.info("Users with ids {}--{} got match", senderId, partnerId);
    }

}
