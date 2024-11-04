package org.example.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.Entities.IntermediaryEntities.ItemStat;

import java.util.List;

@Data
@Entity
@Table(name = "stats")
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name="StatName")
    private String Name;

    @OneToMany(mappedBy = "stats", cascade = CascadeType.ALL)
    private List<ItemStat> itemStats;
}
