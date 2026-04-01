package dev.whitechoke.userService.domain;

import dev.whitechoke.userService.api.dto.ProfileCreateRequestDto;
import dev.whitechoke.userService.api.dto.ProfileCreateResponseDto;
import dev.whitechoke.userService.domain.db.ProfileRepository;
import dev.whitechoke.userService.utils.ProfileMapper;
import dev.whitechoke.userService.utils.ProfileValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileValidator validator;
    private final ProfileMapper mapper;

    @Transactional
    @CachePut(value = "profile", key = "#result.id")
    public ProfileCreateResponseDto createProfile(
            ProfileCreateRequestDto request
    ) {
       validator.validate(request);
       var entity = mapper.toProfileEntity(request);

       entity.setRegisteredAt(Instant.now());
       var saved = profileRepository.save(entity);

       return mapper.toCreateResponseDto(saved);
    }
}
