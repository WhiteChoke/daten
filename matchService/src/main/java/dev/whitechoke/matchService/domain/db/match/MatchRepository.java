package dev.whitechoke.matchService.domain.db.match;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchRepository extends JpaRepository<MatchEntity, MatchId> {

    @Transactional
    @Query(value = """
    INSERT INTO match (first_user_id, second_user_id, first_answer) 
    VALUES (:firstId, :secondId, :isLiked)
    ON CONFLICT (first_user_id, second_user_id) 
    DO UPDATE SET second_answer = :isLiked 
    WHERE match.second_answer IS NULL
    RETURNING *
    """, nativeQuery = true)
    MatchEntity upsertMatch(Long firstId, Long secondId, Boolean isLiked);
}
