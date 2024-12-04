package com.camping_fisa.bouffonduroiapi.services.social;

import com.camping_fisa.bouffonduroiapi.controllers.authentification.dto.UserDto;
import com.camping_fisa.bouffonduroiapi.controllers.social.dto.FriendRequestDto;
import com.camping_fisa.bouffonduroiapi.controllers.social.dto.FriendRequestStatusDto;
import com.camping_fisa.bouffonduroiapi.controllers.social.dto.FriendshipDto;
import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import com.camping_fisa.bouffonduroiapi.entities.social.FriendRequest;
import com.camping_fisa.bouffonduroiapi.entities.social.FriendRequestStatus;
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

    public void sendFriendRequest(Authentication auth, Long receiverId) {
        User sender = authService.authenticate(auth);
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (friendRequestRepository.findBySenderAndReceiver(sender, receiver).isPresent()) {
            throw new ConflictException("Friend request already sent");
        }

        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(FriendRequestStatus.PENDING);
        friendRequestRepository.save(request);
    }

    public List<FriendRequestDto> getPendingRequests(Authentication auth) {
        User user = authService.authenticate(auth);
        return friendRequestRepository.findAllByReceiverAndStatus(user, FriendRequestStatus.PENDING)
                .stream()
                .map(request -> new FriendRequestDto(
                        request.getId(),
                        request.getSender().getId(),
                        request.getReceiver().getId(),
                        request.getStatus()))
                .collect(Collectors.toList());
    }

    public void respondToFriendRequest(Authentication auth, Long requestId, FriendRequestStatusDto statusDto) {
        User receiver = authService.authenticate(auth);
        FriendRequest request = friendRequestRepository.findByIdAndReceiver(requestId, receiver)
                .orElseThrow(() -> new NotFoundException("Friend request not found"));

        FriendRequestStatus status = FriendRequestStatus.valueOf(statusDto.getStatus());
        request.setStatus(status);
        friendRequestRepository.save(request);

        if (status == FriendRequestStatus.ACCEPTED) {
            Friendship friendship = new Friendship();
            friendship.setUser1(request.getSender());
            friendship.setUser2(receiver);
            friendshipRepository.save(friendship);
        }
    }

    public List<FriendshipDto> getFriends(Authentication auth) {
        User user = authService.authenticate(auth);
        return friendshipRepository.findAllByUser(user)
                .stream()
                .map(friendship -> {
                    User friend = friendship.getUser1().equals(user) ? friendship.getUser2() : friendship.getUser1();
                    return new FriendshipDto(friend.getId(), friend.getUsername(), friend.getEmail());
                })
                .collect(Collectors.toList());
    }

    public void deleteFriend(Authentication auth, Long friendId) {
        User user = authService.authenticate(auth);
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Friendship friendship = friendshipRepository.findByUsers(user, friend)
                .orElseThrow(() -> new NotFoundException("Friendship not found"));

        friendshipRepository.delete(friendship);
    }
}

