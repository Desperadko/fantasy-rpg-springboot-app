package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.example.Entities.IntermediaryEntities.PlayerQuest;

import java.util.Set;

@Data
@Entity
@Table(name = "quests")
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;
    @Column(name = "description")
    String descriptionFilePath;

    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;

    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<PlayerQuest> playersWithQuest;
}
