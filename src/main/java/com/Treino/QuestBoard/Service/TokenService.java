package com.Treino.QuestBoard.Service;

import com.Treino.QuestBoard.Domain.Entity.User;
import com.Treino.QuestBoard.Exceptions.UserNotFoundException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); // Quem assina o token
            return JWT.create()
                    .withIssuer("API Quest_Board") // emissor do token
                    .withSubject(user.getEmail()) // O assunto do token/que vem dentro dele nesse caso o email do usuario
                    .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"))) // data de expiração do token
                    .sign(algorithm); // Assina e finaliza o token

        } catch (JWTCreationException exception){
            throw new RuntimeException("ERRO ao gerar token", exception);
        }
    }

    public String getSubject(String tokenJWT){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); // Verifica se foi assinado pelo mesmo criado do token
            return JWT.require(algorithm)
                    .withIssuer("API Quest_Board")
                    .build() // Finaliza a configuração e  cria o objeto responsável por  verificar os tokens
                    .verify(tokenJWT) // Aqui é onde a verificação realmente acontece
                    .getSubject(); // Aqui devolve o email que é o assunto dentro do token, configurado na criação dele

        } catch (JWTVerificationException exception){
            throw new RuntimeException("ERRO!", exception);
        }
    }
}
