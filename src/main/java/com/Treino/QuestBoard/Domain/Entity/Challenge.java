package com.Treino.QuestBoard.Domain.Entity;

import com.Treino.QuestBoard.Domain.Enum.ChallengeDifficulty;
import com.Treino.QuestBoard.Domain.Enum.ChallengeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @NotBlank
    @Enumerated(EnumType.STRING)
    private ChallengeDifficulty difficulty;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    private LocalDate creationDate;

    public Challenge(String title, String description, ChallengeDifficulty difficulty) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.status = ChallengeStatus.OPEN;
        this.creationDate = LocalDate.now();
    }
}
