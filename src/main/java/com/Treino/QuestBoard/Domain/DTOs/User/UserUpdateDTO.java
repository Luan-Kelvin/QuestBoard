package com.Treino.QuestBoard.Domain.DTOs.User;

public record UserUpdateDTO(
        Long id,
        String name,
        String email,
        String password
) {
}
