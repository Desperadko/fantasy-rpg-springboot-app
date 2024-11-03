package org.example.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stats")
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name="StatName")
    private String Name;
}
