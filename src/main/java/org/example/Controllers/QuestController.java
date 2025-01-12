package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.DTOs.QuestDTO;
import org.example.DTOs.QuestDescriptionDTO;
import org.example.Entities.Quest;
import org.example.Services.QuestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/quest")
public class QuestController {
    private final QuestService questService;

    @GetMapping()
    public ResponseEntity<Page<Quest>> getAllQuests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        var quests = questService.getAllQuests(PageRequest.of(page, size));
        return new ResponseEntity<>(quests, HttpStatus.OK);
    }
    @GetMapping(value = "/{questId}")
    public ResponseEntity<Quest> getQuestById(@PathVariable Long questId){
        var quest = questService.getQuestById(questId);
        return new ResponseEntity<>(quest, HttpStatus.OK);
    }
    @GetMapping(value = "/{questId}/description")
    public ResponseEntity<String> getQuestDescriptionById(@PathVariable Long questId){
        var description = questService.getQuestDescriptionById(questId);
        return new ResponseEntity<>(description, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Quest> createQuest(@RequestBody QuestDTO questDTO){
        var quest = questService.createQuest(questDTO);
        return new ResponseEntity<>(quest, HttpStatus.CREATED);
    }
    @PatchMapping(value = "/{questId}")
    public ResponseEntity<Quest> updateQuestName(@RequestParam String questName, @PathVariable Long questId) throws IOException {
        var quest = questService.updateQuestName(questName, questId);
        return new ResponseEntity<>(quest, HttpStatus.ACCEPTED);
    }
    @PatchMapping(value = "/{questId}/description")
    public ResponseEntity<QuestDescriptionDTO> updateQuestDescription(@RequestBody QuestDescriptionDTO questDescriptionDTO, @PathVariable Long questId){
        var questDescription = questService.updateQuestDescription(questDescriptionDTO, questId);
        return new ResponseEntity<>(questDescription, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{questId}")
    public ResponseEntity<?> deleteQuest(@PathVariable Long questId){
        questService.deleteQuest(questId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
