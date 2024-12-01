package com.camping_fisa.bouffonduroiapi.entities.social;

import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "friends")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private User friend;
}
