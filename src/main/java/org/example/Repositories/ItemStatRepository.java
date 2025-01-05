package org.example.Repositories;

import org.example.Entities.Item;
import org.example.Entities.Stat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemStatRepository extends JpaRepository<Stat,Long> {
    //Page<Stat> findAll(Pageable pageable);
    //@Modifying
    //@Query("UPDATE ItemStat is SET is.item = null WHERE is.item.id = :itemId")
    //void removeItemFromAllStats(Long itemId);

    //@Modifying
    //@Query("UPDATE ItemStat is SET is.item = null WHERE is.item IN (:items)")
    //void removeItemsFromAllStats(List<Item> items);

    // @Query("UPDATE ItemStat is SET is.statValue = :value WHERE is.id = :itemStatId")
    //void updateStatValue(Long itemStatId, Integer value);
}
