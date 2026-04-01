package dev.whitechoke.userService.domain.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    Optional<ProfileEntity> findByTelegramId(Long telegramId);
}
