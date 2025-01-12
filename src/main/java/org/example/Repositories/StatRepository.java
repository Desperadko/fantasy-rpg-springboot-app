package org.example.Repositories;

import org.example.Entities.Stat;
import org.example.Entities.IntermediaryEntities.ItemStat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StatRepository extends JpaRepository<Stat, Long> {

    Optional<Stat> findByName(String name);
    List<Stat> findByNameContaining(String namePart);

    List<Stat> findByItemStats(ItemStat itemStat);

    @Query("SELECT s FROM Stat s JOIN s.itemStats is WHERE is.items.id = :itemId")
    List<Stat> findStatsByItemId(Long itemId);

    boolean existsByName(String name);

    Page<Stat> findAll(Pageable pageable);
    Page<Stat> findByNameContaining(String namePart, Pageable pageable);
}