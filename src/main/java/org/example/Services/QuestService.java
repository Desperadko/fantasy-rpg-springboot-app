package org.example.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.DTOs.QuestDTO;
import org.example.DTOs.QuestDescriptionDTO;
import org.example.Entities.Quest;
import org.example.Helpers.QuestFilePathManager;
import org.example.Mappers.QuestMapper;
import org.example.Repositories.LocationRepository;
import org.example.Repositories.QuestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional
public class QuestService {
    private final QuestRepository questRepository;
    private final QuestMapper questMapper;
    private final QuestFilePathManager questFilePathManager;

    private final LocationRepository locationRepository;

    private final ObjectMapper objectMapper;

    public Page<Quest> getAllQuests(Pageable pageable){
        return questRepository.findAll(pageable);
    }
    public Quest getQuestById(Long questId){
        return getQuest(questId);
    }
    public Quest createQuest(QuestDTO questDto){
        var locationName = questDto.locationName();
        var questName = questDto.name();

        if(!locationExists(locationName))
            throw new EntityNotFoundException("Location with name '" + locationName + "' does not exist..");
        if(!isValidQuestName(questName))
            throw new IllegalStateException("Quest names can only contain letters, numbers and spaces. '" + questName + "' is not valid..");

        var questDescription = new QuestDescriptionDTO(questDto.description());
        var filePath = questFilePathManager.createQuestDescriptionFilePath(questName);

        try{
            objectMapper.writeValue(new File(filePath), questDescription);
        }
        catch (IOException e){
            throw new RuntimeException("Error saving quest to file: '" + filePath + "'..", e);
        }

        var quest = questMapper.convertDtoToEntity(questDto, null);
        return questRepository.saveAndFlush(quest);
    }
    public String getQuestDescriptionById(Long questId){
        var questDescriptionFilePath = getQuest(questId).getDescriptionFilePath();

        try{
            Map<String, String> jsonMap = objectMapper.readValue(new File(questDescriptionFilePath), Map.class);
            return jsonMap.get("description");
        }
        catch(IOException e){
            throw new RuntimeException("Error retrieving quest description from file: '" + questDescriptionFilePath + "'..", e);
        }
    }
    public Quest updateQuestName(String questName, Long questId) throws IOException {
        var quest = getQuest(questId);
        var oldQuestDescriptionFilePath = quest.getDescriptionFilePath();

        int rowsUpdated = questRepository.updateQuestName(questName, questId);
        if(rowsUpdated == 0)
            throw new EntityNotFoundException("Quest ID: " + questId);

        var newQuestDescriptionFilePath = questFilePathManager.createQuestDescriptionFilePath(questName);
        questRepository.updateQuestDescriptionFilePath(newQuestDescriptionFilePath, questId);

        var oldQuestDescriptionFile = Path.of(oldQuestDescriptionFilePath);
        var newQuestDescriptionFile = Path.of(newQuestDescriptionFilePath);

        try{
            Files.move(oldQuestDescriptionFile, newQuestDescriptionFile, StandardCopyOption.ATOMIC_MOVE);
        }
        catch (IOException e){
            throw new RuntimeException("Error renaming quest description file: " +
                    "'" + oldQuestDescriptionFilePath + "' " +
                    "to: '" + newQuestDescriptionFilePath + "' .."
                    + e);
        }

        quest.setName(questName);
        quest.setDescriptionFilePath(newQuestDescriptionFilePath);

        return quest;
    }
    public QuestDescriptionDTO updateQuestDescription(QuestDescriptionDTO questDescriptionDTO, Long questId){
        var questDescriptionFilePath = getQuest(questId).getDescriptionFilePath();
        var questDescriptionJson = new File(questDescriptionFilePath);

        try{
            objectMapper.writeValue(questDescriptionJson, questDescriptionDTO);
        }
        catch(IOException e){
            throw new RuntimeException("Error saving quest to file: '" + questDescriptionFilePath + "'..", e);
        }

        try{
            return objectMapper.readValue(questDescriptionJson, QuestDescriptionDTO.class);
        }
        catch (IOException e){
            throw new RuntimeException("Error retrieving quest description from file: '" + questDescriptionFilePath + "'..", e);
        }
    }
    public void deleteQuest(Long questId){
        var quest = getQuest(questId);

        try {
            Files.delete(Paths.get(quest.getDescriptionFilePath()));
        }
        catch (IOException e){
            throw new RuntimeException("Error deleting quest description while deleting quest with name: '" + quest.getName() + "'..");
        }

        var location = quest.getLocation();
        location.getQuests().remove(quest);
        locationRepository.save(location);

        questRepository.delete(quest);
    }

    //utils
    private Quest getQuest(Long questId){
        return questRepository.findById(questId)
                .orElseThrow(() -> new EntityNotFoundException("Quest ID: " + questId));
    }
    private Boolean locationExists(String locationName){
        return locationRepository.existsByName(locationName);
    }
    private boolean isValidQuestName(String questName){
        String specialCharacters = "[^a-zA-z0-9 '-]";
        return !questName.matches(".*" + specialCharacters + ".*");
    }
}
