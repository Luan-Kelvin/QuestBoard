package com.Treino.QuestBoard.Controllers;

import com.Treino.QuestBoard.Domain.DTOs.DadosTokenDTO;
import com.Treino.QuestBoard.Domain.DTOs.User.DadosLoginDTO;
import com.Treino.QuestBoard.Domain.DTOs.User.UserCreateDTO;
import com.Treino.QuestBoard.Domain.DTOs.User.UserResponseDTO;
import com.Treino.QuestBoard.Domain.DTOs.User.UserUpdateDTO;
import com.Treino.QuestBoard.Domain.Entity.User;
import com.Treino.QuestBoard.Service.TokenService;
import com.Treino.QuestBoard.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;


    // REALIZAR LOGIN
    @PostMapping("/login")
    public ResponseEntity<DadosTokenDTO> login(@RequestBody @Valid DadosLoginDTO dto){
        UsernamePasswordAuthenticationToken credenciais = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

        Authentication authentication = manager.authenticate(credenciais);

        String dados = tokenService.gerarToken((User) authentication.getPrincipal());

        return ResponseEntity.ok().body(new DadosTokenDTO(dados));
    }

    // CRIAR NOVO USUÁRIO
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserCreateDTO dto){
        UserResponseDTO responseDTO = userService.createUser(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    // lISTAR USUARIOS CADASTRADOS
    @GetMapping("/list")
    public ResponseEntity<Page<UserResponseDTO>> listUsers(Pageable pageable){
        return ResponseEntity.ok().body(userService.listUsers(pageable));
    }

    // BUSCAR USUÁRIO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userService.findByIdCntroller(id));
    }

    // ATUALIZAR OS DADOS EDITÁVEIS DE USUARIO **SOMENTE USUARIO AUTORIZADO
    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserUpdateDTO dto){
        return ResponseEntity.ok().body(userService.updateUser(dto));
    }
    

}
