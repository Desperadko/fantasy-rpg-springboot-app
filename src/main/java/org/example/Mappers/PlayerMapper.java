package org.example.Mappers;

import jakarta.persistence.EntityNotFoundException;
import org.example.CompositeKeys.PlayerQuestId;
import org.example.DTOs.PlayerDTO;
import org.example.DTOs.QuestDTO;
import org.example.Entities.IntermediaryEntities.PlayerQuest;
import org.example.Entities.Player;
import org.example.Entities.Quest;
import org.example.Repositories.QuestRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { QuestRepository.class })
public interface PlayerMapper {
    @Mapping(target = "name", source = "playerDto.name")
    @Mapping(target = "experience", source = "playerDto.experience")
    @Mapping(target = "account", source = "playerDto.account")
    @Mapping(target = "location", source = "playerDto.location")
    @Mapping(target = "quests", ignore = true)
    @Mapping(target = "id", source = "playerId")
    @Mapping(target = "account.id", source = "accountId")
    @Mapping(target = "location.id", source = "locationId")
    Player convertDtoToEntity(PlayerDTO playerDto, Long playerId, Long accountId, Long locationId);

    default Set<PlayerQuest> mapQuestsToPlayerQuests(
            Set<QuestDTO> questDTOs,
            Player player,
            QuestMapper questMapper,
            QuestRepository questRepository){

        if(questDTOs == null)
            return null;

        if(questDTOs.isEmpty())
            return new HashSet<>();

        return questDTOs.stream()
                .map(questDto -> {
                    var questName = questDto.name();

                    Long questId = questRepository.findQuestIdByName(questName);

                    if(questId == null)
                        throw new EntityNotFoundException("Quest Id not found with Quest Name: '" + questName + "' ..");

                    Quest quest = questMapper.convertDtoToEntity(questDto, questId);

                    var playerQuest = new PlayerQuest();
                    playerQuest.setPlayer(player);
                    playerQuest.setQuest(quest);
                    playerQuest.setId(new PlayerQuestId(player.getId(), questId));

                    return playerQuest;
                })
                .collect(Collectors.toSet());
    }
}
