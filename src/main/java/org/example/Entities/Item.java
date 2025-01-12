package org.example.Entities;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.example.Entities.IntermediaryEntities.ItemStat;

import java.util.List;

@Data
@Entity
@Table(name = "items")
public class Item {

    enum ItemType{
        Thrash(0),
        Equipable(1),
        Usable(2);

        private int type;

        ItemType(int type){
            this.type = type;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "itemName")
    private String itemName;
    @Column(name = "itemType")
    private ItemType itemType;

}
