package dev.whitechoke.userService.domain;

import dev.whitechoke.commonLibs.http.FormResponseDto;
import dev.whitechoke.commonLibs.http.UserPreferencesResponseDto;
import dev.whitechoke.commonLibs.kafka.ProfileCreatedEvent;
import dev.whitechoke.commonLibs.kafka.ProfileDeactivateEvent;
import dev.whitechoke.userService.api.dto.ProfileCreateRequestDto;
import dev.whitechoke.commonLibs.http.ProfileGetByFilterRequestDto;
import dev.whitechoke.commonLibs.http.ProfileResponseDto;
import dev.whitechoke.userService.domain.db.ProfileRepository;
import dev.whitechoke.userService.utils.ProfileMapper;
import dev.whitechoke.userService.utils.ProfileValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileValidator validator;
    private final ProfileMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @CachePut(value = "profile", key = "#result.id")
    public ProfileResponseDto createProfile(
            ProfileCreateRequestDto request
    ) {
       validator.validateCreateRequest(request);
       var entity = mapper.toProfileEntity(request);

       entity.setRegisteredAt(Instant.now());
       var saved = profileRepository.save(entity);

       var event = ProfileCreatedEvent.builder()
               .telegramId(saved.getTelegramId())
               .latitude(saved.getCoordinates().getY())
               .longitude(saved.getCoordinates().getX())
               .maxAge(saved.getMaxAge())
               .minAge(saved.getMinAge())
               .radius(saved.getSearchRadius())
               .gender(saved.getSearchGender())
               .build();

        eventPublisher.publishEvent(event);

       log.info("Created profile with id={}", saved.getId());
       return mapper.toResponseDto(saved);
    }

    @Cacheable(value = "profile", key = "#id")
    public ProfileResponseDto getProfileById(Long id) {
        var entity = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found entity with id=" + id));

        return mapper.toResponseDto(entity);
    }

    public List<Long> getProfilesByFilter(
            ProfileGetByFilterRequestDto request
    ) {
        return  profileRepository.findProfilesByFilter(
                request.latitude(),
                request.longitude(),
                request.searchRadius(),
                request.maxAge(),
                request.minAge(),
                request.gender()
        );
    }

    @Transactional
    @CacheEvict(value = "profile", key = "#result")
    public Long deactivateProfile(Long telegramId) {
        var entity = profileRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new EntityNotFoundException("Not found entity with telegram id=" + telegramId));

        profileRepository.delete(entity);
        
        var event = ProfileDeactivateEvent.builder()
                .id(entity.getId())
                .telegramId(entity.getTelegramId())
                .build();
        
        eventPublisher.publishEvent(event);

        log.info("Deactivated profile with telegram id={}", telegramId);

        return entity.getId();
    }

    public UserPreferencesResponseDto getUserPreferences(Long telegramId) {
        var profile = profileRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new EntityNotFoundException("Not found entity with id=" + telegramId));

        return UserPreferencesResponseDto.builder()
                .latitude(profile.getCoordinates().getY())
                .longitude(profile.getCoordinates().getX())
                .maxAge(profile.getMaxAge())
                .minAge(profile.getMinAge())
                .searchGender(profile.getSearchGender())
                .searchRadius(profile.getSearchRadius())
                .build();
    }

    public FormResponseDto getForm(Long id) {
        var profile = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found entity with id=" + id));

        return FormResponseDto.builder()
                .id(profile.getId())
                .telegramId(profile.getTelegramId())
                .name(profile.getName())
                .birthday(profile.getBirthday())
                .gender(profile.getGender())
                .bio(profile.getBio())
                .build();
    }
}
