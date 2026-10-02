package com.hamza.ecommerce_backend.user.mapper;

import com.hamza.ecommerce_backend.user.DTO.UserCreateDTO;
import com.hamza.ecommerce_backend.user.DTO.UserDTO;
import com.hamza.ecommerce_backend.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper{

    public User toEntity(UserCreateDTO dto){
        User user = new User();
        user.setFirstName(dto.getFirstName());
        if(dto.getMiddleName() != null){
            user.setMiddleName(dto.getMiddleName());
        }
        user.setLastName(dto.getLastName());
        //user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        return user;
    }

    public UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        if(user.getMiddleName() != null){
            userDTO.setMiddleName(user.getMiddleName());
        }
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }

}
