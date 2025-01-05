package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.DTOs.ItemDTO;
import org.example.Entities.Item;
import org.example.Mappers.ItemMapper;
import org.example.Repositories.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private ItemRepository itemRepository;
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
}