package com.camping_fisa.bouffonduroiapi.controllers.social.dto;

import com.camping_fisa.bouffonduroiapi.entities.social.FriendRequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDto {
    private Long id;
    @NotNull
    private Long senderId;
    @NotNull
    private Long receiverId;
    private FriendRequestStatus status;
}
