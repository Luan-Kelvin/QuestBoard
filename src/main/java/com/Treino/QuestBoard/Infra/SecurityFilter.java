package com.Treino.QuestBoard.Infra;

import com.Treino.QuestBoard.Domain.Entity.User;
import com.Treino.QuestBoard.Domain.Respository.UserRepository;
import com.Treino.QuestBoard.Exceptions.UserNotFoundException;
import com.Treino.QuestBoard.Service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    // Request = Requisição que chegou incluido cabeçalhos, URL e metodos HTTP
    // Response = Resposta http que pode ser enviada ao cliente
    // FilterChain = Mecanismo que permite continuaro processamento da requisição
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenObtido = recuperarToken(request); // Recuperar token que veio na rquisição dentro do Authorization

        if (tokenObtido != null){
            String subject = tokenService.getSubject(tokenObtido); // Verifica o Token, válida e devolve o assunto dentro dele
            User user = userRepository.findByEmail(subject).orElseThrow(() -> new UserNotFoundException("ERRO! usuário nõ encontrado")); // Busca usuário pelo conteudo do token

            // User = usuario encontrado no banco de dados
            // null = Credenciais, Como o usuário já foi identificado por meio de um JWT válido, você não precisa armazenar a senha nesse
            // user.getAuthorities() = Obtém as autoridades ou permissões associadas ao usuário.
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            // Conecta o usuario ao contetco de segurança Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Essa linha manda a requisição continuar seu processamento.
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request){
        var token = request.getHeader("Authorization"); // Essa linha lê o cabeçalho HTTP chamado Authorization.
        if (token != null){
            return token.replace("Bearer ", "");
        }

        return null;
    }
}
