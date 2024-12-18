package com.camping_fisa.bouffonduroiapi.services.multiplayer;

import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import com.camping_fisa.bouffonduroiapi.entities.multiplayer.DuelRequest;
import com.camping_fisa.bouffonduroiapi.entities.multiplayer.DuelRequestStatus;
import com.camping_fisa.bouffonduroiapi.exceptions.BadRequestException;
import com.camping_fisa.bouffonduroiapi.exceptions.ConflictException;
import com.camping_fisa.bouffonduroiapi.exceptions.NotFoundException;
import com.camping_fisa.bouffonduroiapi.repositories.authentification.UserRepository;
import com.camping_fisa.bouffonduroiapi.repositories.multiplayer.DuelRequestRepository;
import com.camping_fisa.bouffonduroiapi.repositories.social.FriendshipRepository;
import com.camping_fisa.bouffonduroiapi.services.authentification.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DuelService {
    private final AuthService authService;
    private final FriendshipRepository friendshipRepository;
    private final DuelRequestRepository duelRequestRepository;
    private final UserRepository userRepository;
    private final GameService gameService;

    public void sendDuelRequest(Authentication auth, Long friendId) {
        User sender = authService.authenticate(auth);
        User receiver = userRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Vérifier s'ils sont amis
        if (friendshipRepository.findByUsers(sender, receiver).isEmpty()) {
            throw new BadRequestException("You are not friends with this user");
        }

        // Vérifier s'il n'y a pas déjà une demande en cours
        if (duelRequestRepository.existsBySenderAndReceiverAndStatus(sender, receiver, DuelRequestStatus.PENDING)) {
            throw new ConflictException("Duel request already sent");
        }

        // Créer la demande
        DuelRequest duelRequest = new DuelRequest();
        duelRequest.setSender(sender);
        duelRequest.setReceiver(receiver);
        duelRequest.setStatus(DuelRequestStatus.PENDING);
        duelRequestRepository.save(duelRequest);
    }

    public void respondToDuelRequest(Authentication auth, Long requestId, String status) {
        User receiver = authService.authenticate(auth);
        DuelRequest request = duelRequestRepository.findByIdAndReceiver(requestId, receiver)
                .orElseThrow(() -> new NotFoundException("Duel request not found"));

        try {
            DuelRequestStatus newStatus = DuelRequestStatus.valueOf(status.toUpperCase());
            request.setStatus(newStatus);
            duelRequestRepository.save(request);

            if (newStatus == DuelRequestStatus.ACCEPTED) {
                gameService.createDuelGame(request.getSender(), receiver);
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status: " + status);
        }
    }


}
