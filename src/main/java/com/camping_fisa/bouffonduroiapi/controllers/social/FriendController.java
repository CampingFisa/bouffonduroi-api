package com.camping_fisa.bouffonduroiapi.controllers.social;

import com.camping_fisa.bouffonduroiapi.controllers.authentification.dto.UserDto;
import com.camping_fisa.bouffonduroiapi.controllers.social.dto.FriendRequestDto;
import com.camping_fisa.bouffonduroiapi.controllers.social.dto.FriendRequestStatusDto;
import com.camping_fisa.bouffonduroiapi.services.social.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
@Tag(name = "Friend", description = "Manage friends and friend requests")
public class FriendController {
    private final FriendService friendService;

    @PostMapping("/request")
    @Operation(summary = "Send a friend request")
    public ResponseEntity<Void> sendFriendRequest(@RequestBody @Valid FriendRequestDto friendRequestDto, Authentication authentication) {
        friendService.sendFriendRequest(authentication, friendRequestDto.getReceiverId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/requests")
    @Operation(summary = "Get friend requests")
    public ResponseEntity<List<FriendRequestDto>> getFriendRequests(Authentication authentication) {
        return ResponseEntity.ok(friendService.getFriendRequests(authentication));
    }

    @PatchMapping("/request/{requestId}")
    @Operation(summary = "Respond to a friend request")
    public ResponseEntity<Void> respondToFriendRequest(@PathVariable Long requestId, @RequestBody @Valid FriendRequestStatusDto statusDto, Authentication authentication) {
        friendService.respondToFriendRequest(authentication, requestId, statusDto.getStatus());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get the authenticated user's friends")
    public ResponseEntity<List<UserDto>> getFriends(Authentication authentication) {
        return ResponseEntity.ok(friendService.getFriends(authentication));
    }

    @DeleteMapping("/{friendId}")
    @Operation(summary = "Delete a friend")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long friendId, Authentication authentication) {
        friendService.deleteFriend(authentication, friendId);
        return ResponseEntity.noContent().build();
    }

}
