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

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAll(Pageable pageable);
    Optional<List<Item>> findAllByIdIn(List<Long> itemIds);
    Optional<List<Item>> findItemsByItemName(String itemName);
    @Query("SELECT i FROM Item i JOIN i.itemStats s WHERE s = :stat")
    Optional<List<Item>> findItemsByStat(Stat stat);
    @Query("SELECT i FROM Item i JOIN i.itemStats s WHERE s = :stat")
    Page<Item> findItemsByStat(Stat stat, Pageable pageable);
    // Update name by ID
    @Modifying
    @Query("UPDATE Item i SET i.itemName = :itemName WHERE i.id = :itemId")
    void updateItemName(Long itemId, String itemName);
    // Update itemType by ID
    @Modifying
    @Query("UPDATE Item i SET i.itemType = :itemType WHERE i.id = :itemId")
    void updateItemType(Long itemId, Integer itemType);
    // Remove item ot vs stats
    @Modifying
    @Query("UPDATE ItemStat is SET is.item = null WHERE is.item.id = :itemId")
    void removeItemFromAllStats(Long itemId);
    // Remove multiple itemi ot vsichki stats
    @Modifying
    @Query("UPDATE ItemStat is SET is.item = null WHERE is.item IN (:items)")
    void removeItemsFromAllStats(List<Item> items);
    //Update Stat
    @Query("UPDATE ItemStat is SET is.statValue = :value WHERE is.id = :itemStatId")
    void updateStatValue(Long itemStatId, Integer value);
}
