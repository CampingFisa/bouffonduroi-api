package com.camping_fisa.bouffonduroiapi.entities.multiplayer;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "multiplayer_games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @Column(name = "is_finished", nullable = false)
    private boolean isFinished;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus status;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Round> rounds = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Player winner;
}

