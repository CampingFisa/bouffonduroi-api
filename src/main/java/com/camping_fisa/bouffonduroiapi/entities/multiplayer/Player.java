package com.camping_fisa.bouffonduroiapi.entities.multiplayer;

import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
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
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<PlayerGameHistory> gameHistory;

}
