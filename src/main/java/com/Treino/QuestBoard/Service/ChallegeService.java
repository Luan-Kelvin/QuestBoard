package com.Treino.QuestBoard.Service;

import com.Treino.QuestBoard.Domain.DTOs.Challege.ChallegeCreateDTO;
import com.Treino.QuestBoard.Domain.DTOs.Challege.ChallegeResponseDTO;
import com.Treino.QuestBoard.Domain.Entity.Challenge;
import com.Treino.QuestBoard.Domain.Enum.ChallengeDifficulty;
import com.Treino.QuestBoard.Domain.Enum.ChallengeStatus;
import com.Treino.QuestBoard.Domain.Respository.ChallengeRepository;
import com.Treino.QuestBoard.Exceptions.ChallegeIsAlreadyExistsException;
import com.Treino.QuestBoard.Exceptions.ChallegeNotFoundException;
import com.Treino.QuestBoard.Exceptions.DifficultyInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallegeService {

    private ConversorSerivce conversorSerivce;
    private ChallengeRepository challengeRepository;

    @Transactional
    public ChallegeResponseDTO createChagelle(ChallegeCreateDTO dto){
        ChallengeDifficulty difficulty;

        if (dto.difficulty().equalsIgnoreCase("HARD")){
            difficulty = ChallengeDifficulty.HARD;
        } else if (dto.difficulty().equalsIgnoreCase("EASY")){
            difficulty = ChallengeDifficulty.EASY;
        }else if (dto.difficulty().equalsIgnoreCase("NORMAL")){
            difficulty = ChallengeDifficulty.NORMAL;
        }else {
            throw new DifficultyInvalidException("ERRO! Tipo de dificuldade inválida.");
        }

        Optional<Challenge> challengeRep = challengeRepository.findByTitleAndStatus(dto.title(), ChallengeStatus.OPEN);

        if (challengeRep.isPresent()){
            throw new ChallegeIsAlreadyExistsException("ERRO! Desafio ja existente.");
        }

        Challenge challenge = new Challenge(dto.title(), dto.description(), difficulty);

        challengeRepository.save(challenge);

        return conversorSerivce.converetrChallege(challenge);
    }

    @Transactional
    public Challenge findById(Long id){
        return challengeRepository.findById(id)
                .orElseThrow(() -> new ChallegeNotFoundException(String.format("ERRO! Challenge com ID %s não foi encontrado.", id)));
    }

    @Transactional
    public ChallegeResponseDTO findByIdController(Long id){
        return conversorSerivce.converetrChallege(
                challengeRepository.findById(id)
                        .orElseThrow(() -> new ChallegeNotFoundException(String.format("ERRO! Challenge com ID %s não foi encontrado.", id)))
        );
    }

    @Transactional
    public Page<ChallegeResponseDTO> listChallenges(Pageable pageable){
        Page<Challenge> challenges = challengeRepository.findAll(pageable);

        return challenges.map(conversorSerivce::converetrChallege);
    }

    @Transactional
    public void deletChallenge(Long id){
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new ChallegeNotFoundException(String.format("ERRO! Challenge com Id %s não existe.", id)));

        challengeRepository.delete(challenge);
    }


}
