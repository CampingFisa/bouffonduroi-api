package com.camping_fisa.bouffonduroiapi.repositories.social;

import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import com.camping_fisa.bouffonduroiapi.entities.social.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    // Récupère toutes les amitiés où l'utilisateur est soit user1 soit user2
    @Query("SELECT f FROM Friendship f WHERE (f.user1 = :user AND f.user2 = :friend) OR (f.user1 = :friend AND f.user2 = :user)")
    Optional<Friendship> findFriendshipBetweenUsers(@Param("user") User user, @Param("friend") User friend);

}
