package org.example.Mappers.HelperClasses;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.Entities.Location;
import org.example.Repositories.LocationRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuestMapperHelper {
    private final LocationRepository locationRepository;

    public Location getLocationByName(String locationName){
        return locationRepository.findLocationByName(locationName)
                .orElseThrow(() -> new EntityNotFoundException("Location with name: '" + locationName + "' does not exist."));
    }
}
