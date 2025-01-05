package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_name")
    private String name;

    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Player> players;

    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Quest> quests;

    @Override
    public int hashCode(){
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object other){
        if(this == other) return true;
        if(other == null || getClass() != other.getClass()) return false;

        Location location = (Location)other;
        return Objects.equals(id, location.id)
                && Objects.equals(name, location.name);
    }
}
