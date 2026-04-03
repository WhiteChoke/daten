package dev.whitechoke.userService.domain.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    Optional<ProfileEntity> findByTelegramId(Long telegramId);
    @Query(value = """
    SELECT id FROM profile 
    WHERE ST_DWithin(
        coordinates::geography, 
        ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography, 
        :radiusInMeters
    ) 
    AND is_active = true
    AND gender = :#{#gender.name()}
    AND EXTRACT(YEAR FROM AGE(NOW(), birthday)) BETWEEN :minAge AND :maxAge
    ORDER BY ST_Distance(
        coordinates::geography, 
        ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
    )
    """, nativeQuery = true)
    List<Long> findProfilesByFilter(@Param("lat") Double lat,
                                             @Param("lon") Double lon,
                                             @Param("radiusInMeters") Double radius,
                                             @Param("maxAge") Short maxAge,
                                             @Param("minAge") Short minAge,
                                             @Param("gender") http.Gender gender);
}
