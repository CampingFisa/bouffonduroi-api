package com.camping_fisa.bouffonduroiapi.controllers.multiplayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
    private Long id;
    private boolean isFinished;
    private String status;
    private List<String> playerUsernames;
}