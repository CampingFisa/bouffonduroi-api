package com.camping_fisa.bouffonduroiapi.controllers.social;

import com.camping_fisa.bouffonduroiapi.controllers.social.dto.FriendRequestDto;
import com.camping_fisa.bouffonduroiapi.controllers.social.dto.FriendRequestStatusDto;
import com.camping_fisa.bouffonduroiapi.controllers.social.dto.FriendshipDto;
import com.camping_fisa.bouffonduroiapi.services.social.FriendService;
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
@Tag(name = "Friend", description = "Friendship Management")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/request")
    public ResponseEntity<Void> sendFriendRequest(@Valid @RequestBody FriendRequestDto friendRequestDto, Authentication auth) {
        friendService.sendFriendRequest(auth, friendRequestDto.getReceiverId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/requests")
    public ResponseEntity<List<FriendRequestDto>> getPendingRequests(Authentication auth) {
        return ResponseEntity.ok(friendService.getPendingRequests(auth));
    }

    @PatchMapping("/request/{requestId}")
    public ResponseEntity<Void> respondToRequest(@PathVariable Long requestId, @Valid @RequestBody FriendRequestStatusDto statusDto, Authentication auth) {
        friendService.respondToFriendRequest(auth, requestId, statusDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FriendshipDto>> getFriends(Authentication auth) {
        return ResponseEntity.ok(friendService.getFriends(auth));
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long friendId, Authentication auth) {
        friendService.deleteFriend(auth, friendId);
        return ResponseEntity.noContent().build();
    }
}
