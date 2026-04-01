package dev.whitechoke.userService.domain;

import dev.whitechoke.userService.api.dto.ProfileCreateRequestDto;
import dev.whitechoke.userService.api.dto.ProfileCreateResponseDto;
import dev.whitechoke.userService.domain.db.ProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ProfileMapper {
    ProfileEntity toProfileEntity(ProfileCreateRequestDto request);
    ProfileCreateResponseDto toCreateResponseDto(ProfileEntity entity);
}
