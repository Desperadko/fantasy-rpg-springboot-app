package org.example.Repositories;

import org.example.Entities.Location;
import org.example.Entities.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Page<Player> findAll(Pageable pageable);
    boolean existsByName(String playerName);
    @Modifying
    @Query(value = "UPDATE Player p SET p.experience = p.experience + :experienceValue WHERE p.id = :playerId")
    int incrementPlayerExperience(Long playerId, Long experienceValue);
    @Modifying
    @Query(value = "UPDATE Player p SET p.location = :location WHERE p.id = :playerId")
    int updatePlayerLocation(Long playerId, Location location);
}
