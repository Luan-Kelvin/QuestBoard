package com.Treino.QuestBoard.Domain.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Setter
    private String name;

    @NotBlank
    @Column(unique = true)
    @Setter
    @Pattern(regexp = "^[0-9A-Za-zA-ÿ ,.%$#*]+@[A-Za-z]+\\.[A-Za-z]{2,}$", message = "Email com formato inválido.")
    private String email;

    @NotBlank
    @Setter
    private String password;

    @NotNull
    private LocalDate creationDate;

    @OneToMany(mappedBy = "creatorUser", fetch = FetchType.LAZY)
    private List<Challenge> challengesCreated = new ArrayList<>();

    @ManyToMany(mappedBy = "userParticipated",fetch = FetchType.LAZY)
    private List<Challenge> challengesParticipated = new ArrayList<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.creationDate = LocalDate.now();
    }

    @PrePersist
    public void prePersist(){
        if (this.creationDate == null){
            this.creationDate = LocalDate.now();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }


}
