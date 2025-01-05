package org.example.Mappers;

import org.example.DTOs.ItemDTO;
import org.example.Entities.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "itemName", source = "dto.itemName")
    @Mapping(target = "itemType", expression = "java(Item.ItemType.values()[dto.itemType()])")
    @Mapping(target = "itemStats", ignore = true)
    Item convertDtoToEntity(ItemDTO dto, Long id);

    @Mapping(target = "itemName", source = "itemName")
    @Mapping(target = "itemType", expression = "java(entity.getItemType().ordinal())")
    ItemDTO convertEntityToDto(Item entity);
}