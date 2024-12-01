package com.camping_fisa.bouffonduroiapi.controllers.social.dto;

import lombok.Data;

@Data
public class FriendRequestStatusDto {
    private Long requestId;  // L'ID de la demande
    private String status;  // Statut : "PENDING", "ACCEPTED", "REJECTED"
}