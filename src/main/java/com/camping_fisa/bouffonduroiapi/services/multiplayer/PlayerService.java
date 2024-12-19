package com.camping_fisa.bouffonduroiapi.services.multiplayer;

import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import com.camping_fisa.bouffonduroiapi.entities.multiplayer.Player;
import com.camping_fisa.bouffonduroiapi.exceptions.NotFoundException;
import com.camping_fisa.bouffonduroiapi.repositories.authentification.UserRepository;
import com.camping_fisa.bouffonduroiapi.repositories.multiplayer.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;

    public Player createPlayer(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found for username: " + username));

        Player player = new Player();
        player.setUsername(user.getUsername());
        player.setUser(user);
        player.setTotalScore(0);
        player.setGame(null); // Le jeu sera lié plus tard

        return playerRepository.save(player);
    }

    public Player findPlayerById(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new NotFoundException("Player not found with ID: " + playerId));
    }

    public Player findRandomPlayer() {
        List<Player> players = playerRepository.findAll();
        if (players.isEmpty()) {
            throw new NotFoundException("No players available for random selection");
        }
        return players.get(new Random().nextInt(players.size()));
    }
}
