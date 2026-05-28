package in.medhub.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.medhub.backend.dto.ApiResponse;
import in.medhub.backend.model.Booking;
import in.medhub.backend.model.User;
import in.medhub.backend.services.UserService;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    
    @Autowired
    UserService userService;

    @PostMapping()
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
    	User userCreted= userService.createUser(user);
       	return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("User created successfully",userCreted));
    }
    
    @PostMapping("/book")
    public String slotBooking(@RequestBody Booking book) {
    	return userService.bookSlot(book);
    }
        
}
