package com.camping_fisa.bouffonduroiapi.controllers.social.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDto {
    private Long id; // Peut être null à la création
    @NotNull
    private Long senderId;    // Ne peut pas être null
    @NotNull
    private Long receiverId;  // Ne peut pas être null
    private String status;    // Optionnel pour l'initialisation
}