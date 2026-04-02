package dev.whitechoke.userService.domain;

import dev.whitechoke.userService.api.dto.ProfileCreateRequestDto;
import dev.whitechoke.userService.api.dto.ProfileResponseDto;
import dev.whitechoke.userService.domain.db.ProfileRepository;
import dev.whitechoke.userService.utils.ProfileMapper;
import dev.whitechoke.userService.utils.ProfileValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileValidator validator;
    private final ProfileMapper mapper;
    private final GeometryFactory geometryFactory;

    @Transactional
    @CachePut(value = "profile", key = "#result.id")
    public ProfileResponseDto createProfile(
            ProfileCreateRequestDto request
    ) {
       validator.validate(request);
       var entity = mapper.toProfileEntity(request);

       var coordinates = geometryFactory.createPoint(new Coordinate(request.longitude(), request.latitude()));

       entity.setCoordinates(coordinates);
       entity.setRegisteredAt(Instant.now());
       var saved = profileRepository.save(entity);

       log.info("Created profile with id={}", saved.getId());
       return mapper.toResponseDto(saved);
    }

    @Cacheable(value = "profile", key = "#id")
    public ProfileResponseDto getProfileById(Long id) {
        var entity = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found entity with id=" + id));

        return mapper.toResponseDto(entity);
    }

    @Transactional
    @CacheEvict(value = "profile", key = "#result")
    public Long deactivateProfile(Long telegramId) {
        var entity = profileRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new EntityNotFoundException("Not found entity with telegram id=" + telegramId));

        profileRepository.delete(entity);

        return entity.getId();
    }
}
