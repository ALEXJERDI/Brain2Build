package com.example.brain2build.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", length = 20)
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String prenom;

    private String telephone;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    // Relation avec les rôles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // ✅ Méthodes exigées par UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getNom())) // Exemple : ROLE_ADMIN
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email; // utilisé comme identifiant dans Spring Security
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // tu pourras plus tard ajouter une gestion d'expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // ou ajouter un champ "locked" dans la base
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // on considère que les identifiants ne "périment" pas
    }

    @Override
    public boolean isEnabled() {
        return true; // tu pourrais plus tard désactiver un compte
    }

    @Override
    public String getPassword() {
        return password;
    }

}
