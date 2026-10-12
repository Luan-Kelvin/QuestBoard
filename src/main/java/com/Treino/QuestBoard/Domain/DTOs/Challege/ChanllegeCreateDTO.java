package com.Treino.QuestBoard.Domain.DTOs.Challege;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ChanllegeCreateDTO(
        @NotBlank
        String title,

        String description,

        @NotBlank
        String difficulty,

        @NotBlank
        String status,

        @NotNull
        LocalDate creationDate,

        @NotNull
        Long id_creator_user


) {
}
