package com.hamza.ecommerce_backend.user.service;

import com.hamza.ecommerce_backend.user.DTO.*;
import com.hamza.ecommerce_backend.user.config.SecurityConfig;
import com.hamza.ecommerce_backend.user.entity.Role;
import com.hamza.ecommerce_backend.user.entity.User;
import com.hamza.ecommerce_backend.user.exception.EmailAlreadyExistsException;
import com.hamza.ecommerce_backend.user.mapper.UserMapper;
import com.hamza.ecommerce_backend.user.repository.UserRepository;
import com.hamza.ecommerce_backend.user.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository UserRepo;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticateManager;
    private final JwtService jwtService;

    public UserService(UserRepository UserRepo, UserMapper mapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticateManager, JwtService jwtService){
        this.UserRepo = UserRepo;
        this.mapper = mapper;
        this.passwordEncoder=passwordEncoder;
        this.authenticateManager=authenticateManager;
        this.jwtService=jwtService;
    }

    public UserDTO registerUser(UserCreateDTO dto) {
        Optional<User> findUser=UserRepo.findByEmail(dto.getEmail());
        if(findUser.isPresent()){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user=mapper.toEntity(dto);
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User savedUser=UserRepo.save(user);
        UserDTO responseDTO=mapper.toDTO(savedUser);
        return responseDTO;
    }

    public LoginResponseDTO loginUser(LoginDTO dto){
        UsernamePasswordAuthenticationToken checker=new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        Authentication authentication=authenticateManager.authenticate(checker);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        LoginResponseDTO loginResponseDTO=new LoginResponseDTO();
        loginResponseDTO.setToken(jwtService.generateToken(dto.getEmail(), role));
        loginResponseDTO.setMessage("login Successful");
        return loginResponseDTO;
    }

}
