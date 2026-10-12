package com.Treino.QuestBoard.Domain.Respository;

import com.Treino.QuestBoard.Domain.Entity.Challenge;
import com.Treino.QuestBoard.Domain.Enum.ChallengeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {


    Optional<Challenge> findByTitleAndStatus(String title, ChallengeStatus status);

}
