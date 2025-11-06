package com.example.brain2build.config;

import com.example.brain2build.domain.entity.Ideator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "application.security.jwt.secret=super-secret-key-12345678901234567890123456789012",
        "application.security.jwt.expiration=3600000"
})
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    void shouldGenerateTokenSuccessfully() {
        UserDetails userDetails = new User("test@brain2build.com", "password", Collections.emptyList());
        String token = jwtProvider.generateToken(userDetails);

        assertThat(token).isNotNull();
        assertThat(jwtProvider.validateToken(token, userDetails)).isTrue();
    }
}
