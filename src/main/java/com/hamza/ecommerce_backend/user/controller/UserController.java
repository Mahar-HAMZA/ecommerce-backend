package com.hamza.ecommerce_backend.user.controller;

import com.hamza.ecommerce_backend.user.DTO.UserCreateDTO;
import com.hamza.ecommerce_backend.user.DTO.UserDTO;
import com.hamza.ecommerce_backend.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/register")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping
    public UserDTO registerUser(@RequestBody @Valid UserCreateDTO dto){
        return userService.registerUser(dto);
    }

}
