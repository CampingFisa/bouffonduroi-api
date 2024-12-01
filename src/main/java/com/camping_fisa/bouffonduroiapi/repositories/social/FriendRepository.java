package com.camping_fisa.bouffonduroiapi.repositories.social;

import com.camping_fisa.bouffonduroiapi.entities.social.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findAllByUserId(Long userId);  // Récupérer tous les amis d'un utilisateur

    List<Friend> findAllByFriendId(Long friendId);  // Récupérer tous les utilisateurs ayant comme ami un utilisateur spécifique

    boolean existsByUserIdAndFriendId(Long userId, Long friendId);  // Vérifie si une relation existe
}