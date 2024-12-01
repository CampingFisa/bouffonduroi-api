package com.camping_fisa.bouffonduroiapi.repositories.social;


import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import com.camping_fisa.bouffonduroiapi.entities.social.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findAllByReceiverIdAndStatus(Long receiverId, String status);  // Récupère toutes les demandes reçues avec un statut donné

    List<FriendRequest> findAllBySenderId(Long senderId);  // Récupère toutes les demandes envoyées par un utilisateur

    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);  // Vérifie si une demande existe

    boolean existsBySenderAndReceiver(User sender, User receiver);

    // Récupère toutes les demandes d'ami d'un utilisateur avec un statut spécifique
    List<FriendRequest> findByReceiverAndStatus(User receiver, String status);

    // Récupère une demande d'ami spécifique pour un utilisateur destinataire
    Optional<FriendRequest> findByIdAndReceiver(Long id, User receiver);
}