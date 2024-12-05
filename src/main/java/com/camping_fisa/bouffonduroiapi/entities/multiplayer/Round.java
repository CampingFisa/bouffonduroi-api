package com.camping_fisa.bouffonduroiapi.entities.multiplayer;

import com.camping_fisa.bouffonduroiapi.entities.questions.Theme;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int roundNumber;

    @ManyToOne
    private Game game;

    @ManyToOne
    private Theme theme;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PlayerAnswer> playerAnswers;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Player winner;

}
