package com.camping_fisa.bouffonduroiapi.services.authentification;

import com.camping_fisa.bouffonduroiapi.entities.authentification.User;
import com.camping_fisa.bouffonduroiapi.exceptions.NotFoundException;
import com.camping_fisa.bouffonduroiapi.exceptions.UnauthorizedException;
import com.camping_fisa.bouffonduroiapi.repositories.authentification.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found for email: " + email));
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return jwtService.generateToken(user);
        }
        throw new UnauthorizedException("Invalid login credentials");
    }

    public User authenticate(Authentication authentication) {
        return userRepository.findById(jwtService.getUserIdFromToken(authentication.getPrincipal().toString())).orElseThrow(() -> new UnauthorizedException("Invalid token"));
    }
}