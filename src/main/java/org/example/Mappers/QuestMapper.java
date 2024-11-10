package org.example.Mappers;

import org.example.DTOs.QuestDTO;
import org.example.Entities.Location;
import org.example.Entities.Quest;
import org.example.Mappers.HelperClasses.QuestMapperHelper;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestMapper {
    @Mapping(target = "name", source = "questDto.name")
    @Mapping(target = "descriptionFilePath", source = "questDescriptionFilePath")
    @Mapping(target = "location", source = "questDto.locationName", qualifiedByName = "mapLocationNameToLocation")
    @Mapping(target = "id", source = "questId")
    Quest convertDtoToEntity(QuestDTO questDto, String questDescriptionFilePath, Long questId, @Context QuestMapperHelper questMapperHelper);

    @Named(value = "mapLocationNameToLocation")
    default Location mapLocationNameToLocation(String locationName, @Context QuestMapperHelper questMapperHelper){
        return questMapperHelper.getLocationByName(locationName);
    }
}
