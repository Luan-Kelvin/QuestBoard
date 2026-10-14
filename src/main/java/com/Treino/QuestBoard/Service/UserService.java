package com.Treino.QuestBoard.Service;

import com.Treino.QuestBoard.Domain.DTOs.User.UserCreateDTO;
import com.Treino.QuestBoard.Domain.DTOs.User.UserResponseDTO;
import com.Treino.QuestBoard.Domain.DTOs.User.UserUpdateDTO;
import com.Treino.QuestBoard.Domain.Entity.User;
import com.Treino.QuestBoard.Domain.Respository.UserRepository;
import com.Treino.QuestBoard.Exceptions.UserAlreadyExsistsException;
import com.Treino.QuestBoard.Exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConversorSerivce conversorSerivce;

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO dto){
        Optional<User> userRep = userRepository.findByEmail(dto.email());

        if (userRep.isPresent()){
            throw new UserAlreadyExsistsException(String.format("ERRO! Usuário com emial: %s ja existe.", dto.email()));
        }

        String senhaCriptografada = passwordEncoder.encode(dto.password());

        User user = new User(dto.name(), dto.email(), senhaCriptografada);

        userRepository.save(user);

        return conversorSerivce.converterUser(user);
    }

    @Transactional
    public Page<UserResponseDTO> listUsers(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);

        return users.map(conversorSerivce::converterUser);
    }

    @Transactional
    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("ERRO! Usuário com ID %s não foi encontrado.", id)));
    }

    @Transactional
    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("ERRO! Usuário com email %s não foi encontrado.", email)));
    }

    @Transactional
    public UserResponseDTO findByIdCntroller(Long id){
        return conversorSerivce.converterUser(
                userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(String.format("ERRO! Usuário com ID %s não foi encontrado.", id)))
        );
    }

    @Transactional
    public UserResponseDTO findByEmailController(String email){
        return conversorSerivce.converterUser(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException(String.format("ERRO! Usuário com email %s não foi encontrado.", email)))
        );
    }

    @Transactional
    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("ERRO! Usuário com ID %s não foi encontrado.", id)));

        userRepository.delete(user);
    }

    @Transactional
    public UserResponseDTO updateUser(UserUpdateDTO dto){
        User user = userRepository.findById(dto.id())
                .orElseThrow(() -> new UserNotFoundException(String.format("ERRO! Usuário com ID %s não foi encontrado.", dto.id())));

        if (dto.name() != null){
            user.setName(dto.name());
        }

        if (dto.email() != null){
            user.setEmail(dto.email());
        }

        if (dto.password() != null){
            passwordEncoder.encode(dto.password());
            user.setPassword(dto.password());
        }

        User userSave = userRepository.save(user);

        return conversorSerivce.converterUser(userSave);
    }
}
