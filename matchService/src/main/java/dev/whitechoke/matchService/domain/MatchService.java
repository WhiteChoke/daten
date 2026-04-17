package dev.whitechoke.matchService.domain;

import dev.whitechoke.commonLibs.http.matchDto.AnswerRequestDto;
import dev.whitechoke.commonLibs.http.matchDto.MatchCreateRequestDto;
import dev.whitechoke.commonLibs.kafka.MatchNotificationEvent;
import dev.whitechoke.matchService.domain.db.interaction.InteractionEntity;
import dev.whitechoke.matchService.domain.db.interaction.InteractionRepository;
import dev.whitechoke.matchService.domain.db.match.MatchEntity;
import dev.whitechoke.matchService.domain.db.match.MatchId;
import dev.whitechoke.matchService.domain.db.match.MatchRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final InteractionRepository interactionRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${notification-like-topic}")
    private String notificationLikeTopic;

    @Transactional
    public void createMatch(MatchCreateRequestDto request) {

        var isInteracted = interactionRepository.existsByUserIdAndInteractedUserId(
                request.senderId(),
                request.partnerId()
        );

        if (isInteracted) {
            return;
        }

        var id = new MatchId(
                request.senderId(),
                request.partnerId()
        );

        var entity = matchRepository.upsertMatch(
                id.getFirstUserTelegramId(),
                id.getSecondUserTelegramId(),
                request.isLiked()
        );

        interactionRepository.save(new InteractionEntity(request.senderId(), request.partnerId()));

        sendNotification(entity);

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

        sendNotification(match);
    }

    private void sendNotification(MatchEntity entity) {
        if (Boolean.TRUE.equals(entity.getFirstAnswer()) &&
                Boolean.TRUE.equals(entity.getSecondAnswer())
        ) {
            var event = MatchNotificationEvent.builder()
                    .partnerId(entity.getId().getFirstUserTelegramId())
                    .senderId(entity.getId().getSecondUserTelegramId())
                    .build();

            eventPublisher.publishEvent(event);
        }
    }
}
