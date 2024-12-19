package com.camping_fisa.bouffonduroiapi.controllers.multiplayer;

import com.camping_fisa.bouffonduroiapi.controllers.multiplayer.dto.DuelRequestDto;
import com.camping_fisa.bouffonduroiapi.controllers.multiplayer.dto.DuelRequestStatusDto;
import com.camping_fisa.bouffonduroiapi.controllers.multiplayer.dto.GameDto;
import com.camping_fisa.bouffonduroiapi.entities.multiplayer.DuelRequestStatus;
import com.camping_fisa.bouffonduroiapi.entities.multiplayer.Game;
import com.camping_fisa.bouffonduroiapi.services.multiplayer.DuelService;
import com.camping_fisa.bouffonduroiapi.services.multiplayer.GameService;
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
@RequestMapping("/api/multiplayer")
@RequiredArgsConstructor
@Tag(name = "Multiplayer", description = "Endpoints for multiplayer interactions such as creating duels or retrieving game details.")
public class MultiplayerController {
    private final GameService gameService;
    private final DuelService duelService;

    @PostMapping("/duel/friend")
    @Operation(
            summary = "Create a duel with a friend",
            description = "The authenticated user creates a multiplayer duel with a specific friend.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Game created successfully", content = @Content(schema = @Schema(implementation = GameDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Friend not found")
            }
    )
    public GameDto createDuelWithFriend(@Valid @RequestBody Long friendId, Authentication auth) {
        Game game = gameService.createDuelWithFriend(auth, friendId);
        return gameService.toGameDto(game);
    }

    @GetMapping("/{gameId}")
    @Operation(
            summary = "Get game details",
            description = "Retrieve details of a specific multiplayer game by its ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Game details retrieved", content = @Content(schema = @Schema(implementation = GameDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Game not found")
            }
    )
    public GameDto getGame(@PathVariable Long gameId) {
        return gameService.findGameDtoById(gameId);
    }

    @PostMapping("/{gameId}/finish")
    @Operation(
            summary = "Finish a game",
            description = "Mark a game as finished and determine the winner.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Game finished successfully", content = @Content(schema = @Schema(implementation = Game.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Game not found")
            }
    )
    public Game finishGame(@PathVariable Long gameId) {
        return gameService.finishGameById(gameId);
    }

    @PostMapping("/duel/request")
    @Operation(
            summary = "Send a duel request",
            description = "Sends a duel request to a friend.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Duel request sent"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Friend not found")
            }
    )
    public ResponseEntity<Void> sendDuelRequest(@RequestBody @Valid DuelRequestDto duelRequestDto, Authentication auth) {
        duelService.sendDuelRequest(auth, duelRequestDto.getReceiverId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{gameId}")
    @Operation(
            summary = "Delete a game",
            description = "Delete a specific multiplayer game by its ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Game deleted"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Game not found")
            }
    )
    public ResponseEntity<Void> deleteGame(@PathVariable Long gameId, Authentication auth) {
        gameService.deleteGame(gameId, auth);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/games")
    @Operation(
            summary = "Get all games of the authenticated user",
            description = "Retrieve all multiplayer games associated with the authenticated user.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Games retrieved", content = @Content(schema = @Schema(implementation = GameDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<List<GameDto>> getUserGames(Authentication auth) {
        List<GameDto> games = gameService.getUserGames(auth);
        return ResponseEntity.ok(games);
    }

    @PatchMapping("/duel/request/{requestId}")
    @Operation(
            summary = "Respond to a duel request",
            description = "Accepts or rejects a duel request from a friend.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Duel request responded"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Request not found")
            }
    )
    public ResponseEntity<Void> respondToDuelRequest(@PathVariable Long requestId, @RequestBody @Valid DuelRequestStatusDto statusDto, Authentication auth) {
        duelService.respondToDuelRequest(auth, requestId, statusDto.getStatus());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/duel/sent")
    @Operation(
            summary = "Get duel requests sent by the authenticated user",
            description = "Retrieve all duel requests sent by the authenticated user.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Duel requests retrieved", content = @Content(schema = @Schema(implementation = DuelRequestDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<List<DuelRequestDto>> getSentDuelRequests(Authentication auth) {
        List<DuelRequestDto> sentRequests = duelService.getSentDuelRequests(auth);
        return ResponseEntity.ok(sentRequests);
    }

    @GetMapping("/duel/received")
    @Operation(
            summary = "Get duel requests received by the authenticated user",
            description = "Retrieve all duel requests received by the authenticated user.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Duel requests retrieved", content = @Content(schema = @Schema(implementation = DuelRequestDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<List<DuelRequestDto>> getReceivedDuelRequests(Authentication auth) {
        List<DuelRequestDto> receivedRequests = duelService.getReceivedDuelRequests(auth);
        return ResponseEntity.ok(receivedRequests);
    }


}
