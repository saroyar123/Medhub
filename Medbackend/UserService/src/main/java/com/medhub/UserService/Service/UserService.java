package com.medhub.UserService.Service;

import com.medhub.UserService.Dto.CreateUserDto;
import com.medhub.UserService.Entity.UserEntity;
import com.medhub.UserService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity createUser(CreateUserDto user){
        return userRepository.save(new UserEntity(user));
    }

    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
    }
}
