package com.hamza.ecommerce_backend.user.service;

import com.hamza.ecommerce_backend.user.DTO.UserCreateDTO;
import com.hamza.ecommerce_backend.user.DTO.UserDTO;
import com.hamza.ecommerce_backend.user.config.SecurityConfig;
import com.hamza.ecommerce_backend.user.entity.User;
import com.hamza.ecommerce_backend.user.exception.EmailAlreadyExistsException;
import com.hamza.ecommerce_backend.user.mapper.UserMapper;
import com.hamza.ecommerce_backend.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository UserRepo;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository UserRepo, UserMapper mapper, PasswordEncoder passwordEncoder){
        this.UserRepo = UserRepo;
        this.mapper = mapper;
        this.passwordEncoder=passwordEncoder;
    }

    public UserDTO registerUser(UserCreateDTO dto) {
        Optional<User> findUser=UserRepo.findByEmail(dto.getEmail());
        if(findUser.isPresent()){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user=mapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User savedUser=UserRepo.save(user);
        UserDTO responseDTO=mapper.toDTO(savedUser);
        return responseDTO;
    }

}
