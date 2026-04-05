package dev.whitechoke.matchService.domain.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MarchRepository extends JpaRepository<MatchEntity, Long> {
}
