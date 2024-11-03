package org.example.Entities.IntermediaryEntities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "itemStats")
public class ItemStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
