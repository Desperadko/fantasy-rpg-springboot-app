package org.example.Services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.CompositeKeys.PlayerQuestId;
import org.example.DTOs.PlayerCreateDTO;
import org.example.DTOs.PlayerDTO;
import org.example.Entities.Account;
import org.example.Entities.IntermediaryEntities.PlayerQuest;
import org.example.Entities.Location;
import org.example.Entities.Player;
import org.example.Entities.Quest;
import org.example.Helpers.StaticVariables;
import org.example.Mappers.AccountMapper;
import org.example.Mappers.PlayerMapper;
import org.example.Mappers.QuestMapper;
import org.example.Repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    private final QuestRepository questRepository;
    private final QuestMapper questMapper;

    private final LocationRepository locationRepository;

    private final PlayerQuestRepository playerQuestRepository;

    public Page<Player> getAllPlayers(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }
    public Player getPlayerById(Long playerId) {
        return getPlayer(playerId);
    }
    public Player createPlayer(PlayerCreateDTO playerCreateDTO) {
        var playerName = playerCreateDTO.name();
        var accountId = playerCreateDTO.accountId();

        if(playerRepository.existsByName(playerName))
            throw new EntityExistsException("Player with name: '" + playerName + "' already exists..");
        var account = getAccount(accountId);
        var accountDTO = accountMapper.convertEntityToDto(account);
        var startingZoneId = locationRepository.findLocationIdByName(StaticVariables.startingZone);

        var playerDTO = new PlayerDTO(playerName, accountDTO);
        var player = playerMapper.convertDtoToEntity(playerDTO, null, accountId, startingZoneId);

        player = playerRepository.saveAndFlush(player);

        var questDTOs = playerDTO.getQuests();
        var playerQuests = playerMapper.mapQuestsToPlayerQuests(questDTOs, player, questMapper, questRepository);

        player.setQuests(playerQuests);

        return playerRepository.saveAndFlush(player);
    }
    public Player addExperience(Long playerId, Long experienceValue){
        var rowsUpdated = playerRepository.incrementPlayerExperience(playerId, experienceValue);
        if(rowsUpdated == 0)
            throw new EntityNotFoundException("Player ID: " + playerId);

        return getPlayer(playerId);
    }
    public Player changeLocation(Long playerId, Long locationId){
        var location = getLocation(locationId);

        var rowsUpdated = playerRepository.updatePlayerLocation(playerId, location);
        if(rowsUpdated == 0)
            throw new EntityNotFoundException("Player ID: " + playerId);

        return getPlayer(playerId);
    }
    public Set<PlayerQuest> addQuest(Long playerId, Long questId){
        var player = getPlayer(playerId);
        var quest = getQuest(questId);
        var playerQuestLog = player.getQuests();

        boolean alreadyAssigned = playerQuestLog.stream()
                .anyMatch(playerQuest -> playerQuest.getQuest().getId().equals(questId));
        if(alreadyAssigned)
            throw new IllegalStateException("Quest already assigned. Quest ID: " + questId);

        PlayerQuest playerQuest = new PlayerQuest();
        playerQuest.setPlayer(player);
        playerQuest.setQuest(quest);
        playerQuest.setId(new PlayerQuestId(playerId, questId));

        playerQuestLog.add(playerQuest);

        return playerQuestLog;
    }
    public Set<PlayerQuest> removeQuest(Long playerId, Long questId){
        var playerQuest = getPlayerQuest(playerId, questId);
        var player = getPlayer(playerId);
        var playerQuestLog = player.getQuests();

        playerQuestLog.remove(playerQuest);

        return playerQuestLog;
    }
    public void deletePlayer(Long playerId){
        var player = getPlayer(playerId);

        var account = player.getAccount();
        account.getAccountCharacters().remove(player);
        accountRepository.save(account);

        var location = player.getLocation();
        location.getPlayers().remove(player);
        locationRepository.save(location);

        playerRepository.delete(player);
    }

    //utils
    private Player getPlayer(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player ID: " + playerId));
    }
    private Location getLocation(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location ID: " + locationId));
    }
    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account ID: " + accountId));
    }
    private Quest getQuest(Long questId){
        return questRepository.findById(questId)
                .orElseThrow(() -> new EntityNotFoundException("Quest ID: " + questId));
    }
    private PlayerQuest getPlayerQuest(Long playerId, Long questId){
        return playerQuestRepository.findById(new PlayerQuestId(playerId, questId))
                .orElseThrow(() -> new EntityNotFoundException("Quest not found in Player's quest log. Player ID: " + playerId + " Quest ID: " + questId));
    }
}
