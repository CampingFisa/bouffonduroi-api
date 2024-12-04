package com.camping_fisa.bouffonduroiapi.controllers.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendshipDto {
    private Long friendId;
    private String username;
    private String email;
}
