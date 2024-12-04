package com.camping_fisa.bouffonduroiapi.controllers.multiplayer;

import com.camping_fisa.bouffonduroiapi.entities.multiplayer.Game;
import com.camping_fisa.bouffonduroiapi.services.multiplayer.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/multiplayer")
@RequiredArgsConstructor
public class MultiplayerController {

    private final GameService gameService;

    @PostMapping("/duel/friend")
    public Game createDuelWithFriend(@RequestBody String friendUsername, Authentication auth) {
        return gameService.createDuelWithFriend(auth, friendUsername);
    }

    @PostMapping("/duel/random")
    public Game createDuelWithRandomPlayer(Authentication auth) {
        return gameService.createDuelWithRandomPlayer(auth);
    }

    @GetMapping("/{gameId}")
    public Game getGame(@PathVariable Long gameId) {
        return gameService.findGameById(gameId);
    }

    @PostMapping("/{gameId}/finish")
    public Game finishGame(@PathVariable Long gameId) {
        return gameService.finishGameById(gameId);
    }
}

