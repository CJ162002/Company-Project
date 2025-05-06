package com.demo.maindata.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.maindata.Repository.UserRepository;
import com.demo.maindata.entity.UserEntity;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepo;

    // Get all user data
    @GetMapping("/alldata")
    public ResponseEntity<?> getAllData() {
        return ResponseEntity.ok().body(userRepo.findAll());
    }

    // Add new user data
    @PostMapping("/add")
    public ResponseEntity<String> addData(@RequestBody UserEntity request) {
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());
        user.setUserTask(request.getUserTask());
        userRepo.save(user);
        return new ResponseEntity<>("Data saved successfully.", HttpStatus.CREATED);
    }

    // Update existing user data
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateData(@PathVariable("id") int id, @RequestBody UserEntity request) {
        Optional<UserEntity> opt = userRepo.findById(id);
        if (!opt.isPresent()) {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
        UserEntity user = opt.get();
        user.setName(request.getName());
        user.setRoles(request.getRoles());
        user.setUserTask(request.getUserTask());
        userRepo.save(user);
        return new ResponseEntity<>("Data updated for ID: " + id, HttpStatus.OK);
    }

    // Delete user data
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteData(@PathVariable("id") int id) {
        Optional<UserEntity> opt = userRepo.findById(id);
        if (!opt.isPresent()) {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
        userRepo.deleteById(id);
        return new ResponseEntity<>("Data deleted for ID: " + id, HttpStatus.OK);
    }
}
