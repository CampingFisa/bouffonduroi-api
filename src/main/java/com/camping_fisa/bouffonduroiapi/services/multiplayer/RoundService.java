package com.camping_fisa.bouffonduroiapi.services.multiplayer;

import com.camping_fisa.bouffonduroiapi.entities.multiplayer.Game;
import com.camping_fisa.bouffonduroiapi.entities.multiplayer.Round;
import com.camping_fisa.bouffonduroiapi.entities.questions.Theme;
import com.camping_fisa.bouffonduroiapi.repositories.multiplayer.RoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;

    public Round createRound(Game game, Theme theme, int roundNumber) {
        Round round = new Round();
        round.setGame(game);
        round.setTheme(theme);
        round.setRoundNumber(roundNumber);
        return roundRepository.save(round);
    }

    public List<Round> findRoundsByGame(Game game) {
        return roundRepository.findAll();
    }
}
