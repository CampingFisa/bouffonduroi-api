package com.camping_fisa.bouffonduroiapi.repositories.social;


import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import com.camping_fisa.bouffonduroiapi.entities.social.FriendRequest;
import com.camping_fisa.bouffonduroiapi.entities.social.FriendRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Optional<FriendRequest> findBySenderAndReceiver(User sender, User receiver);

    List<FriendRequest> findAllByReceiverAndStatus(User receiver, FriendRequestStatus status);

    Optional<FriendRequest> findByIdAndReceiver(Long id, User receiver);
}
