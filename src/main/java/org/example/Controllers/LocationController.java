package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.DTOs.LocationDTO;
import org.example.Entities.Location;
import org.example.Services.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/location")
public class LocationController {
    private final LocationService locationService;

    @GetMapping()
    public ResponseEntity<Page<Location>> getLocations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        var locations = locationService.getAllLocations(PageRequest.of(page, size));
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }
    @GetMapping(value = "/{locationId}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long locationId){
        var location = locationService.getLocationById(locationId);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Location> createLocation(LocationDTO locationDTO){
        var location = locationService.createLocation(locationDTO);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }
    @PatchMapping(value = "/{locationId}")
    public ResponseEntity<Location> updateLocation(LocationDTO locationDTO, @PathVariable Long locationId){
        var location = locationService.updateLocation(locationDTO, locationId);
        return new ResponseEntity<>(location, HttpStatus.ACCEPTED);
    }
    @DeleteMapping(value = "/{locationId}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long locationId){
        locationService.deleteLocation(locationId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
