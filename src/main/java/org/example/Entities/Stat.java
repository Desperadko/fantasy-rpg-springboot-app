package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;

    @Column(name="stat_name")
    private String name;

    @OneToMany(mappedBy = "stat",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ItemStat> itemStats;
}