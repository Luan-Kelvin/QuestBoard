package com.Treino.QuestBoard.Domain.Respository;

import com.Treino.QuestBoard.Domain.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
