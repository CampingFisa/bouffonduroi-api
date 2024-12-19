package com.camping_fisa.bouffonduroiapi.controllers.multiplayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuelRequestDto {
    private Long id;
    private Long senderId;
    private String senderUsername;
    private Long receiverId;
    private String receiverUsername;
    private String status; // PENDING, ACCEPTED, REJECTED
}
