package com.Treino.QuestBoard.Domain.DTOs.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserCreateDTO(
        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp = "^[0-9A-Za-zA-ÿ ,.%$#*]+@[A-Za-z]+\\.[A-Za-z]{2,}$" , message = "Formato de email inválido")
        String email,

        @NotBlank
        String password
) {
}
