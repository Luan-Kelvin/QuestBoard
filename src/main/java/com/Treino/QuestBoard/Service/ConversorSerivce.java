package com.Treino.QuestBoard.Service;

import com.Treino.QuestBoard.Domain.DTOs.User.UserResponseDTO;
import com.Treino.QuestBoard.Domain.Entity.User;
import org.springframework.stereotype.Service;

@Service
public class ConversorSerivce {

    public UserResponseDTO converterUser(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreationDate(),
                user.getChallengesCreated(),
                user.getChallengesParticipated()
        );
    }
}
