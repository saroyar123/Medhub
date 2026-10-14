package com.medhub.UserService.Controller;

import com.medhub.UserService.Dto.CreateUserDto;
import com.medhub.UserService.Entity.UserEntity;
import com.medhub.UserService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class User {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getUser(String id){
        return "hello";
    }

    @PostMapping
    public UserEntity createUser(@RequestBody CreateUserDto user){
        return userService.createUser(user);
    }

    @GetMapping("/email")
    public UserEntity getUserByEmail(@RequestParam String email){
        return userService.getUserByEmail(email);
    }
}
