package com.camping_fisa.bouffonduroiapi.controllers.social.dto;

import lombok.Data;

@Data
public class FriendResponseDto {
    private Long friendId;  // L'ID de l'ami
    private String username;  // Nom d'utilisateur de l'ami
    private String email;  // Email de l'ami
}