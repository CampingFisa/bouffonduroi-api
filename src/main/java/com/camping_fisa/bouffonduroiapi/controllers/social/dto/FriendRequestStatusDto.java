package com.camping_fisa.bouffonduroiapi.controllers.social.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FriendRequestStatusDto {
    @NotNull
    private String status; // "PENDING", "ACCEPTED", "REJECTED"
}
