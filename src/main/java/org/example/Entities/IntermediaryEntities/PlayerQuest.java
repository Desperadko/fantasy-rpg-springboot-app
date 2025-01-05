package org.example.Entities.IntermediaryEntities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.CompositeKeys.PlayerQuestId;
import org.example.Entities.Player;
import org.example.Entities.Quest;

@Data
@Entity
@Table(name = "player_quests")
public class PlayerQuest {
    @EmbeddedId
    private PlayerQuestId id;

    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @MapsId("questId")
    @JoinColumn(name = "quest_id")
    private Quest quest;
}
