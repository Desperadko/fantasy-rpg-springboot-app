package org.example.Repositories;

import org.example.Entities.Location;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findAll(Pageable pageable);
    Optional<Location> findLocationByName(String locationName);
    @Query(value = "SELECT l.id FROM Location l WHERE l.name = :locationName")
    Long findLocationIdByName(String locationName);
    Boolean existsByName(String locationName);
}
