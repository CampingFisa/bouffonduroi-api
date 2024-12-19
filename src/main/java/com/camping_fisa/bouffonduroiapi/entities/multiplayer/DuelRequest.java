package com.camping_fisa.bouffonduroiapi.entities.multiplayer;

import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "duel_request")
public class DuelRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @Enumerated(EnumType.STRING)
    private DuelRequestStatus status; // PENDING, ACCEPTED, REJECTED

    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

}
