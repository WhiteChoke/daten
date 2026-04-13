package dev.whitechoke.userService.utils;

import dev.whitechoke.userService.api.dto.ProfileCreateRequestDto;
import dev.whitechoke.commonLibs.http.profileDto.ProfileResponseDto;
import dev.whitechoke.userService.domain.db.ProfileEntity;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class ProfileMapper {

    @Autowired
    protected GeometryFactory geometryFactory;

    @Mapping(target = "coordinates", source = ".", qualifiedByName = "coordinatesToPoint")
    public abstract ProfileEntity toProfileEntity(ProfileCreateRequestDto request);
    @Mapping(target = "latitude", source = "coordinates.y")
    @Mapping(target = "longitude", source = "coordinates.x")
    public abstract ProfileResponseDto toResponseDto(ProfileEntity entity);

    @Named("coordinatesToPoint")
    protected Point coordinatesToPoint(ProfileCreateRequestDto request) {
        return geometryFactory.createPoint(new Coordinate(request.longitude(), request.latitude()));
    }
}
