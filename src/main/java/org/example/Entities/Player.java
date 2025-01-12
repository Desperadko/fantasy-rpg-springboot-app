package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.Entities.IntermediaryEntities.PlayerQuest;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;
    @Column(name = "experience")
    private long experience;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "player", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<PlayerQuest> quests;

    @Override
    public int hashCode(){
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object other){
        if(this == other) return true;
        if(other == null || getClass() != other.getClass()) return false;

        Player player = (Player)other;
        return experience == player.experience
                && Objects.equals(id, player.id)
                && Objects.equals(name, player.name);
    }
}
