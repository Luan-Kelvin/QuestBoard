package com.Treino.QuestBoard.Domain.Entity;

import com.Treino.QuestBoard.Domain.Enum.ChallengeDifficulty;
import com.Treino.QuestBoard.Domain.Enum.ChallengeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "challenge")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ChallengeDifficulty difficulty;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    @NotNull
    private LocalDate creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_creator_user")
    private User creatorUser;

    @ManyToMany
    @JoinTable(
            name = "challenge_participants",
            joinColumns = @JoinColumn(name = "id_challenge", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_user", nullable = false)
    )
    private List<User> userParticipated = new ArrayList<>();

    public Challenge(String title, String description, ChallengeDifficulty difficulty) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.status = ChallengeStatus.OPEN;
        this.creationDate = LocalDate.now();
    }

    @PrePersist
    public void prePersist(){
        if (this.creationDate == null){
            this.creationDate = LocalDate.now();
        }
    }
}
