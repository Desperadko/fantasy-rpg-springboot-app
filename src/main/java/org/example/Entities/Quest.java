package org.example.Entities;

import jakarta.persistence.*;
import lombok.Data;

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
}
