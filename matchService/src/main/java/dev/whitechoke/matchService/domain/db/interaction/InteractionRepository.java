package dev.whitechoke.matchService.domain.db.interaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionRepository extends JpaRepository<InteractionEntity, Long> {
    boolean existsByUserIdAndInteractedUserId(Long userId, Long interactedUserId);
}
