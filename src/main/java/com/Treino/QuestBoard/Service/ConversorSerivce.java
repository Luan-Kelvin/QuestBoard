package com.Treino.QuestBoard.Service;

import com.Treino.QuestBoard.Domain.DTOs.Challege.ChallegeResponseDTO;
import com.Treino.QuestBoard.Domain.DTOs.User.UserResponseDTO;
import com.Treino.QuestBoard.Domain.Entity.Challenge;
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

    public ChallegeResponseDTO converetrChallege(Challenge challenge){
        return new ChallegeResponseDTO(
                challenge.getId(),
                challenge.getTitle(),
                challenge.getDescription(),
                challenge.getDifficulty(),
                challenge.getStatus(),
                challenge.getCreationDate(),
                challenge.getCreatorUser().getId(),
                challenge.getUserParticipated().stream().map(this::converterUser).toList()
        );
    }
}
