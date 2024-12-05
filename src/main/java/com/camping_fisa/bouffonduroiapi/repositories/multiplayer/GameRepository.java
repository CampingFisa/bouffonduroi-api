package com.camping_fisa.bouffonduroiapi.repositories.multiplayer;

import com.camping_fisa.bouffonduroiapi.entities.multiplayer.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
