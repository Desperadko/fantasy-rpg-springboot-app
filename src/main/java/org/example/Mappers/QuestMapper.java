package org.example.Mappers;

import org.example.DTOs.QuestDTO;
import org.example.Entities.Quest;
import org.example.Helpers.QuestFilePathManager;
import org.example.Helpers.QuestMapperHelper;
import org.example.Repositories.LocationRepository;
import org.example.Repositories.QuestRepository;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { QuestMapperHelper.class, QuestFilePathManager.class })
public interface QuestMapper {
    @Mapping(target = "name", source = "questDto.name")
    @Mapping(target = "descriptionFilePath", source = "questDto.name", qualifiedByName = "getQuestFilePath")
    @Mapping(target = "location", source = "questDto.locationName", qualifiedByName = "findLocationByName")
    @Mapping(target = "id", source = "questId")
    Quest convertDtoToEntity(QuestDTO questDto, Long questId);
}
