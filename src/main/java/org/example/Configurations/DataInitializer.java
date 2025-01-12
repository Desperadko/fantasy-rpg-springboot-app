package org.example.Configurations;

import org.example.Entities.Location;
import org.example.Helpers.StaticVariables;
import org.example.Repositories.LocationRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public ApplicationRunner initializeStartingZone(LocationRepository locationRepository){
        return args -> {

            if(!locationRepository.existsByName(StaticVariables.startingZone)){
                Location startingZone = new Location();
                startingZone.setName(StaticVariables.startingZone);

                locationRepository.saveAndFlush(startingZone);
            }
        };
    }
}
