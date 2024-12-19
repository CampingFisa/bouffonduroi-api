package com.camping_fisa.bouffonduroiapi.services.multiplayer;

import com.camping_fisa.bouffonduroiapi.controllers.multiplayer.dto.GameDto;
import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import com.camping_fisa.bouffonduroiapi.entities.multiplayer.Game;
import com.camping_fisa.bouffonduroiapi.entities.multiplayer.GameStatus;
import com.camping_fisa.bouffonduroiapi.entities.multiplayer.Player;
import com.camping_fisa.bouffonduroiapi.entities.multiplayer.PlayerGameHistory;
import com.camping_fisa.bouffonduroiapi.exceptions.BadRequestException;
import com.camping_fisa.bouffonduroiapi.exceptions.NotFoundException;
import com.camping_fisa.bouffonduroiapi.repositories.authentification.UserRepository;
import com.camping_fisa.bouffonduroiapi.repositories.multiplayer.GameRepository;
import com.camping_fisa.bouffonduroiapi.services.authentification.AuthService;
import com.camping_fisa.bouffonduroiapi.services.social.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private final AuthService authService;
    private final FriendService friendService;
    private final UserRepository userRepository;

    public Game createDuelWithFriend(Authentication auth, Long friendId) {
        User currentUser = authService.authenticate(auth);

        // Récupérer l'ami à partir de l'id de l'ami
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException("User not found for friendId: " + friendId, UserRepository.class));

        // Créer les joueurs
        Player player1 = playerService.createPlayer(currentUser.getUsername());
        Player player2 = playerService.createPlayer(friend.getUsername());

        // Créer la partie
        return createGame(List.of(player1, player2));
    }

    public Game createDuelGame(User player1User, User player2User) {
        Player player1 = playerService.createPlayer(player1User.getUsername());
        Player player2 = playerService.createPlayer(player2User.getUsername());

        return createGame(List.of(player1, player2));
    }


    private Game createGame(List<Player> players) {
        Game game = new Game();
        game.setPlayers(new ArrayList<>());
        game.setRounds(new ArrayList<>());
        game.setStatus(GameStatus.ONGOING);

        // Lier chaque joueur au jeu
        for (Player player : players) {
            player.setGame(game);
            game.getPlayers().add(player);
        }

        // Sauvegarder le jeu
        return gameRepository.save(game);
    }


    public void deleteGame(Long gameId, Authentication auth) {
        User user = authService.authenticate(auth);
        Game game = findGameById(gameId);

        boolean isParticipant = game.getPlayers().stream()
                .anyMatch(player -> player.getUsername().equals(user.getUsername()));

        if (!isParticipant) {
            throw new BadRequestException("You are not authorized to delete this game");
        }

        gameRepository.delete(game);
    }

    public List<GameDto> getUserGames(Authentication auth) {
        User user = authService.authenticate(auth);

        List<Game> games = gameRepository.findAll().stream()
                .filter(game -> game.getPlayers().stream()
                        .anyMatch(player -> player.getUsername().equals(user.getUsername())))
                .toList();

        return games.stream()
                .map(this::toGameDto)
                .toList();
    }


    public Game findGameById(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Game not found with ID: " + gameId));
    }

    public Game finishGameById(Long gameId) {
        Game game = findGameById(gameId);
        return finishGame(game);
    }

    public Game finishGame(Game game) {
        Player winner = game.getPlayers().stream()
                .max((p1, p2) -> Integer.compare(p1.getTotalScore(), p2.getTotalScore()))
                .orElseThrow(() -> new IllegalStateException("No players found"));

        game.setWinner(winner);
        game.setFinished(true);
        game.setStatus(GameStatus.FINISHED);

        // Ajoutez l'historique
        game.getPlayers().forEach(player -> {
            PlayerGameHistory history = new PlayerGameHistory();
            history.setPlayer(player);
            history.setGame(game);
            history.setWinner(player.equals(winner));
            history.setScore(player.getTotalScore());
            history.setRoundsWon((int) game.getRounds().stream()
                    .filter(round -> round.getWinner().equals(player))
                    .count());
            player.getGameHistory().add(history);
        });

        return gameRepository.save(game);
    }
    public GameDto findGameDtoById(Long gameId) {
        Game game = findGameById(gameId);
        return toGameDto(game);
    }
    public GameDto toGameDto(Game game) {
        List<String> playerUsernames = game.getPlayers().stream()
                .map(Player::getUsername)
                .toList();

        return new GameDto(
                game.getId(),
                game.isFinished(),
                game.getStatus().toString(),
                playerUsernames
        );
    }
}

