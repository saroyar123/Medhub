package in.medhub.backend.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.medhub.backend.auth.JwtProvider;
import in.medhub.backend.dto.ApiResponse;
import in.medhub.backend.dto.LoginDto;
import in.medhub.backend.model.Booking;
import in.medhub.backend.model.User;
import in.medhub.backend.services.UserService;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
    	
    	user.setPassword(passwordEncoder.encode(user.getPassword()));   	
    	User userCreted= userService.createUser(user);
    	
       	return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("User created successfully",userCreted));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@RequestBody LoginDto loginDto){
    	
    	System.out.println("from login controller");
    	
    	// 1. Authenticate user credentials manually using AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword())
        );

        // 2. Generate the JWT string
        String token = jwtProvider.generateToken(authentication);
        // 3. Fetch user information to return alongside the token
        User user = userService.findByUserName(loginDto.getUserName());
        user.setPassword(null); // Safety cleanup

        // 4. Pack both token and profile into a map response
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Login Successfull", data));
    	
//    	String userName=authentication.getName();
//    	
//    	User user=userService.findByUserName(userName);
//    	
//    	user.setPassword(null);
//    	return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Login Successfull", user));
    }
    
    @PostMapping("/book")
    public String slotBooking(@RequestBody Booking book) {
    	return userService.bookSlot(book);
    }
        
}
