package org.example.authservice.service.Auth;

import lombok.RequiredArgsConstructor;
import org.example.authservice.domain.entity.AuthUser;
import org.example.authservice.repositorie.AuthUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthUser user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())  // password hash
                .roles(user.getRole().replace("ROLE_", "")) // ROLE_WORKER → WORKER
                .build();
    }
}
