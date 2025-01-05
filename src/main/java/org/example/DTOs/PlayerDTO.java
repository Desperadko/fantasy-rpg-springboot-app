package org.example.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Helpers.StaticVariables;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private String name;
    private long experience = 0L;
    private AccountDTO account;
    private LocationDTO location = new LocationDTO(StaticVariables.startingZone);
    private Set<QuestDTO> quests = new HashSet<>();

    public PlayerDTO(String name, AccountDTO account){
        this.name = name;
        this.account = account;
    }
}
