package org.example.Mappers;

import org.example.DTOs.LocationDTO;
import org.example.Entities.Location;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LocationMapperTest {

    private final LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);

    @Test
    void convertDtoToEntity_Success() {
        var locationDTO = new LocationDTO("Meden Kurnik");
        var locationId = 3L;

        var location = locationMapper.convertDtoToEntity(locationDTO, locationId);

        assertThat(location).isNotNull();
        assertThat(location.getName()).isEqualTo("Meden Kurnik");
        assertThat(location.getId()).isEqualTo(3L);
    }
}