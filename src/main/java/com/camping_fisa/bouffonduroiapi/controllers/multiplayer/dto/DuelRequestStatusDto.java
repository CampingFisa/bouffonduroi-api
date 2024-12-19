package com.camping_fisa.bouffonduroiapi.controllers.multiplayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuelRequestStatusDto {
    private String status; // Example values: "PENDING", "ACCEPTED", "REJECTED"
}
