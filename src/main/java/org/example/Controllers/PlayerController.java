package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.DTOs.PlayerCreateDTO;
import org.example.Entities.IntermediaryEntities.PlayerQuest;
import org.example.Entities.Player;
import org.example.Services.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/player")
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping()
    public ResponseEntity<Page<Player>> getAllPlayers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        var players = playerService.getAllPlayers(PageRequest.of(page, size));
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
    @GetMapping(value = "/{playerId}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long playerId){
        var player = playerService.getPlayerById(playerId);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Player> createPlayer(@RequestBody PlayerCreateDTO playerCreateDTO){
        var player = playerService.createPlayer(playerCreateDTO);
        return new ResponseEntity<>(player, HttpStatus.CREATED);
    }
    @PatchMapping(value = "{playerId}/addExperience")
    public ResponseEntity<Player> addExperience(@PathVariable Long playerId, @RequestParam Long experienceValue){
        var player = playerService.addExperience(playerId, experienceValue);
        return new ResponseEntity<>(player, HttpStatus.ACCEPTED);
    }
    @PatchMapping(value = "{playerId}/changeLocation")
    public ResponseEntity<Player> changeLocation(@PathVariable Long playerId, @RequestParam Long locationId){
        var player = playerService.changeLocation(playerId, locationId);
        return new ResponseEntity<>(player, HttpStatus.ACCEPTED);
    }
    @PostMapping(value = "{playerId}/questLog")
    public ResponseEntity<Set<PlayerQuest>> addQuestToQuestLog(@PathVariable Long playerId, @RequestParam Long questId){
        var playerQuests = playerService.addQuest(playerId, questId);
        return new ResponseEntity<>(playerQuests, HttpStatus.ACCEPTED);
    }
    @DeleteMapping(value = "{playerId}/questLog")
    public ResponseEntity<Set<PlayerQuest>> removeQuestFromQuestLog(@PathVariable Long playerId, @RequestParam Long questId){
        var playerQuests = playerService.removeQuest(playerId, questId);
        return new ResponseEntity<>(playerQuests, HttpStatus.ACCEPTED);
    }
    @DeleteMapping(value = "/{playerId}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long playerId){
        playerService.deletePlayer(playerId);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }
}
