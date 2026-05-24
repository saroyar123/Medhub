package in.medhub.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.medhub.backend.model.User;
import in.medhub.backend.repository.DoctorRepository;
import in.medhub.backend.repository.DoctorsAvailabilityRepository;
import in.medhub.backend.repository.UserRepository;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    DoctorRepository doctorRepository;
    
    @Autowired
    DoctorsAvailabilityRepository doctorsAvailabilityRepository;

    @PostMapping("/user")
    public User createUser() {
        User user = new User();
        user.setUserName("Saroyar");
        user.setPassword("123");

        return userRepository.save(user);
    }
    
    
    
}
