package org.example.Entities.IntermediaryEntities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.Entities.Item;
import org.example.Entities.Stat;

@Data
@Entity
@Table(name = "itemStat")
public class ItemStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stat_value")
    private Integer statValue;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "stat_id", nullable = false)
    private Stat stat;
}