package com.camping_fisa.bouffonduroiapi.controllers.multiplayer;

import com.camping_fisa.bouffonduroiapi.controllers.multiplayer.dto.GameDto;
import com.camping_fisa.bouffonduroiapi.entities.multiplayer.Game;
import com.camping_fisa.bouffonduroiapi.services.multiplayer.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/multiplayer")
@RequiredArgsConstructor
@Tag(name = "Multiplayer", description = "Endpoints for multiplayer interactions such as creating duels or retrieving game details.")
public class MultiplayerController {

    private final GameService gameService;

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


    @PostMapping("/duel/random")
    @Operation(
            summary = "Create a duel with a random player",
            description = "The authenticated user creates a multiplayer duel with a random player.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Game created successfully", content = @Content(schema = @Schema(implementation = Game.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public Game createDuelWithRandomPlayer(Authentication auth) {
        return gameService.createDuelWithRandomPlayer(auth);
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
}
