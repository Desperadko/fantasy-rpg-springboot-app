package org.example.Repositories;

import org.example.Entities.IntermediaryEntities.ItemStat;
import org.example.Entities.Item;
import org.example.Entities.Stat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemStatRepository extends JpaRepository<ItemStat, Long> {

    List<ItemStat> findByItems(Item item);
    List<ItemStat> findByStats(Stat stat);
    Optional<ItemStat> findByItemsAndStats(Item item, Stat stat);

    List<ItemStat> findByStatValue(Integer value);
    List<Object> findByStatValueGreaterThan(Integer value);
    List<Object> findByStatValueLessThan(Integer value);

    List<Object> findByItems_Id(Long itemId);
    List<ItemStat> findByStats_ID(Long statId);

    @Modifying
    @Query("UPDATE ItemStat is SET is.statValue = :value WHERE is.id = :id")
    void updateStatValue(Long id, Integer value);

    @Modifying
    @Query("UPDATE ItemStat is SET is.statValue = :value WHERE is.items.id = :itemId AND is.stats.ID = :statId")
    void updateStatValue(Long itemId, Long statId, Integer value);

    void deleteByItems(Item item);
    void deleteByStats(Stat stat);
    void deleteByItemsAndStats(Item item, Stat stat);

    Page<ItemStat> findByItems(Item item, Pageable pageable);
    Page<ItemStat> findByStats(Stat stat, Pageable pageable);
}