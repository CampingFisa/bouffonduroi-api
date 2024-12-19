package com.camping_fisa.bouffonduroiapi.entities.multiplayer;

import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private int totalScore;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<PlayerGameHistory> gameHistory;

}
