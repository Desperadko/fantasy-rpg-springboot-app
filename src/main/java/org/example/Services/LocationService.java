package org.example.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.DTOs.LocationDTO;
import org.example.Entities.Location;
import org.example.Mappers.LocationMapper;
import org.example.Repositories.LocationRepository;
import org.example.Repositories.QuestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    private final QuestService questService;
    private final QuestRepository questRepository;
    
    public Page<Location> getAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    public Location getLocationById(Long locationId) {
        return getLocation(locationId);
    }
    public Location createLocation(LocationDTO locationDTO) {
        var locationName = locationDTO.name();
        if(locationRepository.existsByName(locationName))
            throw new IllegalStateException("Location with name '" + locationName + "' already exists..");

        return saveLocationDtoToDatabase(locationDTO, null);
    }
    public Location updateLocation(LocationDTO locationDTO, Long locationId) {
        if(!locationRepository.existsById(locationId))
            throw new EntityNotFoundException("Location ID: " + locationId);

        return saveLocationDtoToDatabase(locationDTO, locationId);
    }
    public void deleteLocation(Long locationId) {
        var location = getLocation(locationId);
        var questIds = questRepository.findAllQuestIdsByLocation(location);

        for(var questId : questIds){
            questService.deleteQuest(questId);
        }

        locationRepository.delete(location);
    }

    //utils
    private Location getLocation(Long locationId){
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location ID: " + locationId));
    }
    private Location saveLocationDtoToDatabase(LocationDTO locationDTO, Long locationId) {
        var location = locationMapper.convertDtoToEntity(locationDTO, locationId);
        return locationRepository.saveAndFlush(location);
    }
}
