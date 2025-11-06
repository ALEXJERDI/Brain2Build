package com.example.brain2build.config.security;

import com.example.brain2build.repository.UserRepository;
import com.example.brain2build.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads user by ID (passed as String) instead of email.
     * This matches the new JWT design where 'sub' = userId.
     */
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // ✅ Try to interpret as user ID (for JWT validation)
        try {
            Long id = Long.parseLong(identifier);
            return userRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
        } catch (NumberFormatException e) {
            // ✅ Otherwise, treat as email (for login)
            return userRepository.findByEmail(identifier)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + identifier));
        }
    }

}
