package com.camping_fisa.bouffonduroiapi.repositories.authentification;

import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}