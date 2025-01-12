package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.DTOs.ItemDTO;
import org.example.Entities.Item;
import org.example.Entities.Stat;
import org.example.Mappers.ItemMapper;
import org.example.Repositories.ItemRepository;
import org.example.Repositories.ItemStatRepository;
import org.example.Repositories.StatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private ItemRepository itemRepository;
    private ItemStatRepository itemStatRepository;
    private StatRepository statRepository;
    private ItemMapper itemMapper;

    @GetMapping
    public ResponseEntity<Page<ItemDTO>> getAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var items = itemRepository.findAll(PageRequest.of(page, size))
                .map(itemMapper::convertEntityToDto);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        return itemRepository.findById(id)
                .map(itemMapper::convertEntityToDto)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDTO) {
        Item item = itemMapper.convertDtoToEntity(itemDTO, null);
        Item savedItem = itemRepository.save(item);
        return new ResponseEntity<>(itemMapper.convertEntityToDto(savedItem), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        if (!itemRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Item item = itemMapper.convertDtoToEntity(itemDTO, id);
        Item updatedItem = itemRepository.save(item);
        return new ResponseEntity<>(itemMapper.convertEntityToDto(updatedItem), HttpStatus.OK);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<ItemDTO> updateItemName(@PathVariable Long id, @RequestParam String name) {
        if (!itemRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        itemRepository.setItemName(id, name);
        return itemRepository.findById(id)
                .map(item -> new ResponseEntity<>(itemMapper.convertEntityToDto(item), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        if (!itemRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        itemRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-names/{itemName}")
    public ResponseEntity<List<ItemDTO>> getItemsByName(@PathVariable String itemName) {
        return itemRepository.findByItemName(itemName)
                .map(items -> items.stream()
                        .map(itemMapper::convertEntityToDto)
                        .collect(Collectors.toList()))
                .map(dtos -> new ResponseEntity<>(dtos, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-ids")
    public ResponseEntity<List<ItemDTO>> getItemsByIds(@RequestParam List<Long> ids) {
        return itemRepository.findAllByIdIn(ids)
                .map(items -> items.stream()
                        .map(itemMapper::convertEntityToDto)
                        .collect(Collectors.toList()))
                .map(dtos -> new ResponseEntity<>(dtos, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-stat/{statId}")
    public ResponseEntity<Page<ItemDTO>> getItemsByStat(
            @PathVariable Long statId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Stat stat = statRepository.findById(statId)
                .orElseThrow(() -> new RuntimeException("Stat not found"));

        Page<Item> items = itemRepository.findByItemStats(stat, PageRequest.of(page, size));
        return new ResponseEntity<>(items.map(itemMapper::convertEntityToDto), HttpStatus.OK);
    }

    @GetMapping("/{itemId}/stats")
    public ResponseEntity<List<Stat>> getItemStats(@PathVariable Long itemId) {
        List<Stat> stats = statRepository.findStatsByItemId(itemId);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    @GetMapping("/{itemId}/stat-values")
    public ResponseEntity<List<Object>> getItemStatValues(@PathVariable Long itemId) {
        List<Object> itemStats = itemStatRepository.findByItem_Id(itemId);
        return new ResponseEntity<>(itemStats, HttpStatus.OK);
    }

    @PatchMapping("/{itemId}/stats/{statId}")
    public ResponseEntity<?> updateItemStatValue(
            @PathVariable Long itemId,
            @PathVariable Long statId,
            @RequestParam Integer value
    ) {
        itemStatRepository.updateStatValue(itemId, statId, value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/stats/value-greater-than")
    public ResponseEntity<List<Object>> getItemsWithStatGreaterThan(@RequestParam Integer value) {
        List<Object> itemStats = itemStatRepository.findByStatValueGreaterThan(value);
        return new ResponseEntity<>(itemStats, HttpStatus.OK);
    }

    @GetMapping("/stats/value-less-than")
    public ResponseEntity<List<Object>> getItemsWithStatLessThan(@RequestParam Integer value) {
        List<Object> itemStats = itemStatRepository.findByStatValueLessThan(value);
        return new ResponseEntity<>(itemStats, HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}/stats/{statId}")
    public ResponseEntity<?> deleteItemStat(
            @PathVariable Long itemId,
            @PathVariable Long statId
    ) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        Stat stat = statRepository.findById(statId)
                .orElseThrow(() -> new RuntimeException("Stat not found"));

        itemStatRepository.deleteByItemAndStat(item, stat);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{itemId}/stats")
    public ResponseEntity<?> deleteAllItemStats(@PathVariable Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        itemStatRepository.deleteByItem(item);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

