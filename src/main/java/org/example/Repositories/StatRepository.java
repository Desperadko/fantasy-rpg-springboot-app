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

    @Query("SELECT s FROM Stat s JOIN s.itemStats is WHERE is.item.id = :itemId")
    List<Stat> findStatsByItemId(Long itemId);
    Page<Stat> findAll(Pageable pageable);

}