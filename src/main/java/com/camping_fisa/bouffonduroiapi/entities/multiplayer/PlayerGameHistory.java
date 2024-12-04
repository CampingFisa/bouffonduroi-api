package com.camping_fisa.bouffonduroiapi.entities.multiplayer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PlayerGameHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Game game;

    private boolean isWinner;

    private int score;

    private int roundsWon;
}
