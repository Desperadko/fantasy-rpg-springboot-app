package org.example.Mappers;

import org.example.DTOs.LocationDTO;
import org.example.Entities.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {
    @Mapping(target = "name", source = "locationDto.name")
    @Mapping(target = "id", source = "locationId")
    Location convertDtoToEntity(LocationDTO locationDto, Long locationId);
}
