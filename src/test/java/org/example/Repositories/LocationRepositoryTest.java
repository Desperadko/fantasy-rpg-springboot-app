package org.example.Repositories;

import org.example.Entities.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LocationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void cleanup(){
        jdbcTemplate.execute("DELETE FROM locations");
        jdbcTemplate.execute("ALTER TABLE locations ALTER COLUMN id RESTART WITH 1");
    }

    @Test
    void saveLocation_Success(){
        var location = new Location();
        location.setName("Lapa Dundi");

        var savedLocation = locationRepository.save(location);

        assertThat(savedLocation.getId()).isNotNull();
        assertThat(savedLocation.getName()).isEqualTo("Lapa Dundi");
    }

    @Test
    void findLocationByName_Success() {
        var location1 = new Location();
        location1.setName("Lapa Dundi");
        locationRepository.save(location1);

        var location2 = new Location();
        location2.setName("Mimasa");
        locationRepository.save(location2);

        var foundLocation = locationRepository.findLocationByName("Mimasa");

        assertThat(foundLocation).isPresent();
        assertThat(foundLocation.get().getName()).isEqualTo("Mimasa");
    }

    @Test
    void findLocationIdByName_Success() {
        var location1 = new Location();
        location1.setName("Lapa Dundi");
        locationRepository.save(location1);

        var location2 = new Location();
        location2.setName("Mimasa");
        locationRepository.save(location2);

        var locationId = locationRepository.findLocationIdByName("Mimasa");

        assertThat(locationId).isEqualTo(2L);
    }

    @Test
    void existsByName_Success() {
        var location1 = new Location();
        location1.setName("Lapa Dundi");
        locationRepository.save(location1);

        var location2 = new Location();
        location2.setName("Mimasa");
        locationRepository.save(location2);

        var locationExists = locationRepository.existsByName("Lapa Dundi");
        var locationDoesNotExist = locationRepository.existsByName("Fantaka");

        assertThat(locationExists).isEqualTo(true);
        assertThat(locationDoesNotExist).isEqualTo(false);
    }

    @Test
    void deleteLocation_Success(){
        var location = new Location();
        location.setName("Meden Kurnik");
        var savedLocation = locationRepository.save(location);

        locationRepository.delete(savedLocation);

        var deletedLocation = locationRepository.findById(savedLocation.getId());
        assertThat(deletedLocation).isEmpty();
    }
}