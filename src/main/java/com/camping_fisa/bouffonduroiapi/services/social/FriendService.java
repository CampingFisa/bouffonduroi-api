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
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new NotFoundException("User not found"));

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
                .orElseThrow(() -> new NotFoundException("Request not found"));

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
        List<Friendship> friendships = friendshipRepository.findByUser1OrUser2(user, user);

        return friendships.stream()
                .map(friendship -> {
                    User friend = friendship.getUser1().equals(user) ? friendship.getUser2() : friendship.getUser1();
                    return new UserDto(friend.getId(), friend.getUsername(), null, friend.getEmail(), friend.getAddress(), friend.isAdmin());
                })
                .collect(Collectors.toList());
    }

    public void deleteFriend(Authentication authentication, Long friendId) {
        User user = authService.authenticate(authentication);
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Friendship friendship = friendshipRepository
                .findByUser1OrUser2(user, friend)
                .stream()
                .filter(f -> (f.getUser1().equals(user) && f.getUser2().equals(friend)) ||
                        (f.getUser1().equals(friend) && f.getUser2().equals(user)))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Friendship not found"));

        friendshipRepository.delete(friendship);
    }


}
