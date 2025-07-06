package fr.alexpado.bravediver.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private Set<Stratagem> stratagems = new HashSet<>();

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getUsername() {

        return this.username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public Set<Stratagem> getStratagems() {

        return this.stratagems;
    }

    public void setStratagems(Set<Stratagem> unlocks) {

        this.stratagems = unlocks;
    }

}
