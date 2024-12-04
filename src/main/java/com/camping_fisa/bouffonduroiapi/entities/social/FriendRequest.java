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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendRequestStatus status; // ENUM: PENDING, ACCEPTED, REJECTED

    @Override
    public String toString() {
        return "FriendRequest{" +
                "id=" + id +
                ", status=" + status +
                ", senderId=" + (sender != null ? sender.getId() : null) +
                ", receiverId=" + (receiver != null ? receiver.getId() : null) +
                '}';
    }

}

