package org.example.Helpers;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.Entities.Location;
import org.example.Repositories.LocationRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuestMapperHelper {
    LocationRepository locationRepository;

    @Named("findLocationByName")
    public Location findLocationByName(String locationName){
        return locationRepository.findLocationByName(locationName)
                .orElseThrow(() -> new EntityNotFoundException("Location with name '" + locationName + "' does not exist.."));
    }
}
