package com.Treino.QuestBoard.Domain.DTOs.User;

import com.Treino.QuestBoard.Domain.Entity.Challenge;

import java.time.LocalDate;
import java.util.List;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        LocalDate creationDate,
        List<Challenge> challengesCreated,
        List<Challenge> challengesParticipated
) {
}
