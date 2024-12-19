package com.camping_fisa.bouffonduroiapi.controllers.social;

import com.camping_fisa.bouffonduroiapi.controllers.social.dto.FriendRequestDto;
import com.camping_fisa.bouffonduroiapi.controllers.social.dto.FriendRequestStatusDto;
import com.camping_fisa.bouffonduroiapi.controllers.social.dto.FriendshipDto;
import com.camping_fisa.bouffonduroiapi.services.social.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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
    @Operation(
            summary = "Send a friend request",
            description = "Sends a friend request to another user.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Friend request sent"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public ResponseEntity<Void> sendFriendRequest(@Valid @RequestBody FriendRequestDto friendRequestDto, Authentication auth) {
        friendService.sendFriendRequest(auth, friendRequestDto.getReceiverId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/requests")
    @Operation(
            summary = "Get pending friend requests",
            description = "Retrieves all pending friend requests for the authenticated user.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pending requests retrieved", content = @Content(schema = @Schema(implementation = FriendRequestDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<List<FriendRequestDto>> getPendingRequests(Authentication auth) {
        return ResponseEntity.ok(friendService.getPendingRequests(auth));
    }

    @PatchMapping("/request/{requestId}")
    @Operation(
            summary = "Respond to a friend request",
            description = "Accepts or rejects a friend request.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Request responded to"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Request not found")
            }
    )
    public ResponseEntity<Void> respondToRequest(@PathVariable Long requestId, @Valid @RequestBody FriendRequestStatusDto statusDto, Authentication auth) {
        friendService.respondToFriendRequest(auth, requestId, statusDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(
            summary = "Get friends",
            description = "Retrieves the list of friends for the authenticated user.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Friends retrieved", content = @Content(schema = @Schema(implementation = FriendshipDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<List<FriendshipDto>> getFriends(Authentication auth) {
        return ResponseEntity.ok(friendService.getFriends(auth));
    }

    @DeleteMapping("/{friendId}")
    @Operation(
            summary = "Delete a friend",
            description = "Removes a friend from the authenticated user's friend list.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Friend deleted"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Friend not found")
            }
    )
    public ResponseEntity<Void> deleteFriend(@PathVariable Long friendId, Authentication auth) {
        friendService.deleteFriend(auth, friendId);
        return ResponseEntity.noContent().build();
    }
}
