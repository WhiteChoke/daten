package dev.whitechoke.matchService.domain.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchEntity, MatchId> {
}
