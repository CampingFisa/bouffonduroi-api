package com.camping_fisa.bouffonduroiapi.entities.social;

import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "friend_requests")
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(nullable = false)
    private String status; // Possible values: PENDING, ACCEPTED, REJECTED
}
