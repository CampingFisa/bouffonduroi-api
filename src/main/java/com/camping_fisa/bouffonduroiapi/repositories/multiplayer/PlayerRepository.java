package com.camping_fisa.bouffonduroiapi.repositories.multiplayer;

import com.camping_fisa.bouffonduroiapi.entities.multiplayer.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
