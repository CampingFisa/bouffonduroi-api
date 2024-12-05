package com.camping_fisa.bouffonduroiapi.entities.multiplayer;

import com.camping_fisa.bouffonduroiapi.entities.questions.Question;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PlayerAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Question question;

    private boolean isCorrect;

    private int pointsAwarded;
}
