package org.example.Repositories;

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

    Optional<List<Item>> findAllByIdIn(List<Long> itemIds);
    Optional<List<Item>> findByItemName(String itemName);
    Optional<List<Item>> findByItemStats(Stat stat);
    Page<Item> findByItemStats(Stat stat, Pageable pageable);
    @Modifying
    @Query("UPDATE Item i SET i.itemName = :itemName WHERE i.id = :itemId")
    void setItemName(Long itemId, String itemName);

}