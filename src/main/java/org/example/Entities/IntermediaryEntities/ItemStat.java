package org.example.Entities.IntermediaryEntities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.Entities.Item;
import org.example.Entities.Stat;

@Data
@Entity
@Table(name = "itemStats")
public class ItemStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "stat_id", nullable = false)
    private Stat stats;
}
