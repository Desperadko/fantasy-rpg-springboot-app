package org.example.Repositories;

import org.example.CompositeKeys.PlayerQuestId;
import org.example.Entities.IntermediaryEntities.PlayerQuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerQuestRepository extends JpaRepository<PlayerQuest, PlayerQuestId> {
    Optional<List<PlayerQuest>> findByPlayerId(Long playerId);
    Optional<List<PlayerQuest>> findByQuestId(Long questId);
}
