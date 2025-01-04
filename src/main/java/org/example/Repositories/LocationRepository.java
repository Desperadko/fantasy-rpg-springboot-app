package org.example.Repositories;

import org.example.Entities.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findAll(Pageable pageable);
    Optional<Location> findLocationByName(String locationName);
    Boolean existsByName(String locationName);
}
