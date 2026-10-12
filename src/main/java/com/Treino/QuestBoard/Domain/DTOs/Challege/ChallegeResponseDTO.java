package com.Treino.QuestBoard.Domain.DTOs.Challege;


import com.Treino.QuestBoard.Domain.DTOs.User.UserResponseDTO;
import com.Treino.QuestBoard.Domain.Enum.ChallengeDifficulty;
import com.Treino.QuestBoard.Domain.Enum.ChallengeStatus;

import java.time.LocalDate;
import java.util.List;

public record ChallegeResponseDTO(
        Long id,

        String title,

        String description,

        ChallengeDifficulty difficulty,

        ChallengeStatus status,

        LocalDate creationDate,

        Long id_creatorUser,

        List<UserResponseDTO> userParticipated
) {
}
