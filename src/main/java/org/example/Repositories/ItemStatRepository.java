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

    //List<ItemStat> findByItem(Item item);
    //List<ItemStat> findByStat(Stat stat);
    //Optional<ItemStat> findByItemAndStat(Item item, Stat stat);

    List<Object> findByStatValueGreaterThan(Integer value);
    List<Object> findByStatValueLessThan(Integer value);

    List<Object> findByItem_Id(Long itemId);
    void deleteByItem(Item item);
    void deleteByItemAndStat(Item item, Stat stat);

    @Modifying
    @Query("UPDATE ItemStat is SET is.statValue = :value WHERE is.item.id = :itemId AND is.stat.id = :statId")
    void updateStatValue(Long itemId, Long statId, Integer value);

    //Page<ItemStat> findByItem(Item item, Pageable pageable);
    //Page<ItemStat> findByStat(Stat stat, Pageable pageable);
}