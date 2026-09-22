package com.hamza.ecommerce_backend.user.service;

import com.hamza.ecommerce_backend.user.entity.User;
import com.hamza.ecommerce_backend.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        Optional<User> user=userRepository.findByEmail(email);
        if(user.isPresent()){
            User existUser=user.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(existUser.getEmail())
                    .password(existUser.getPassword())
                    .build();
        }
        throw new UsernameNotFoundException("User does not exist");
    }
}
