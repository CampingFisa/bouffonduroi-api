package com.camping_fisa.bouffonduroiapi.repositories.social;

import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import com.camping_fisa.bouffonduroiapi.entities.social.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    // Récupère toutes les amitiés où l'utilisateur est soit user1 soit user2
    List<Friendship> findByUser1OrUser2(User user1, User user2);
}
