package com.camping_fisa.bouffonduroiapi.services.social;

import com.camping_fisa.bouffonduroiapi.controllers.authentification.dto.UserDto;
import com.camping_fisa.bouffonduroiapi.controllers.social.dto.FriendRequestDto;
import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import com.camping_fisa.bouffonduroiapi.entities.social.FriendRequest;
import com.camping_fisa.bouffonduroiapi.entities.social.Friendship;
import com.camping_fisa.bouffonduroiapi.exceptions.BadRequestException;
import com.camping_fisa.bouffonduroiapi.exceptions.ConflictException;
import com.camping_fisa.bouffonduroiapi.exceptions.NotFoundException;
import com.camping_fisa.bouffonduroiapi.repositories.authentification.UserRepository;
import com.camping_fisa.bouffonduroiapi.repositories.social.FriendRequestRepository;
import com.camping_fisa.bouffonduroiapi.repositories.social.FriendshipRepository;
import com.camping_fisa.bouffonduroiapi.services.authentification.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final AuthService authService;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public void sendFriendRequest(Authentication authentication, Long receiverId) {
        User sender = authService.authenticate(authentication);
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new NotFoundException(receiverId.toString(), User.class));

        if (friendRequestRepository.existsBySenderAndReceiver(sender, receiver)) {
            throw new ConflictException("Friend request already exists");
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setStatus("PENDING");
        friendRequestRepository.save(friendRequest);
    }

    public List<FriendRequestDto> getFriendRequests(Authentication authentication) {
        User receiver = authService.authenticate(authentication);
        return friendRequestRepository.findByReceiverAndStatus(receiver, "PENDING")
                .stream()
                .map(request -> new FriendRequestDto(request.getId(), request.getSender().getId(), receiver.getId(), request.getStatus()))
                .collect(Collectors.toList());
    }

    public void respondToFriendRequest(Authentication authentication, Long requestId, String status) {
        User receiver = authService.authenticate(authentication);
        FriendRequest request = friendRequestRepository.findByIdAndReceiver(requestId, receiver)
                .orElseThrow(() -> new NotFoundException(requestId.toString(), FriendRequest.class));

        if (!"ACCEPTED".equals(status) && !"REJECTED".equals(status)) {
            throw new BadRequestException("Invalid status");
        }

        request.setStatus(status);
        friendRequestRepository.save(request);

        if ("ACCEPTED".equals(status)) {
            Friendship friendship = new Friendship();
            friendship.setUser1(request.getSender());
            friendship.setUser2(receiver);
            friendshipRepository.save(friendship);
        }
    }

    public List<UserDto> getFriends(Authentication authentication) {
        User user = authService.authenticate(authentication);
        Optional<Friendship> friendships = friendshipRepository.findFriendshipBetweenUsers(user, user);

        return friendships.stream()
                .map(friendship -> {
                    User friend = friendship.getUser1().equals(user) ? friendship.getUser2() : friendship.getUser1();
                    return new UserDto(
                            friend.getId(),
                            friend.getUsername(),
                            null, // Suppression du mot de passe
                            friend.getEmail(),
                            friend.getAddress(),
                            friend.isAdmin()
                    );
                })
                .collect(Collectors.toList());
    }



    public void deleteFriend(Authentication authentication, Long friendId) {
        // Authentification de l'utilisateur
        User user = authService.authenticate(authentication);

        // Vérification de l'existence de l'ami
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException("Friend with ID " + friendId + " not found", User.class));

        // Recherche de la relation d'amitié
        Friendship friendship = friendshipRepository.findFriendshipBetweenUsers(user, friend)
                .orElseThrow(() -> new NotFoundException("Friendship between user " + user.getId() + " and friend " + friend.getId(), Friendship.class));

        // Suppression de la relation d'amitié
        friendshipRepository.delete(friendship);
    }




}
