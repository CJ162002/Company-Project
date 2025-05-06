package com.demo.maindata.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.maindata.DTO.LoginRequest;
import com.demo.maindata.Repository.UserRepository;
import com.demo.maindata.entity.UserEntity;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    // View user profile
    @PostMapping("/data")
    public ResponseEntity<?> getUserData(Principal principal) {
//    	System.out.println("-----------"+principal.getName());
        Optional<UserEntity> opt = userRepo.findByEmail(principal.getName());
        if (opt.isPresent()) {
            UserEntity user = opt.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
