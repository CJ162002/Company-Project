package com.demo.maindata.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.maindata.Repository.UserRepository;
import com.demo.maindata.entity.UserEntity;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private UserRepository userRepo;

    // View all users and their assigned tasks
    @GetMapping("/alldata")
    public ResponseEntity<?> getAllUsersWithTasks() {
        Iterable<UserEntity> users = userRepo.findAll();
        StringBuilder response = new StringBuilder();
        
        users.forEach(user -> {
            response.append("User: ").append(user.getName())
                    .append(", Assigned Task: ").append(user.getUserTask()).append("\n");
        });
        
        if (response.length() == 0) {
            return new ResponseEntity<>("No users found.", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(response.toString());
    }

    // Assign tasks to users
    @PostMapping("/assignTask/{id}")
    public ResponseEntity<String> assignTaskToUser(@PathVariable("id") int id, @RequestBody String task) {
        Optional<UserEntity> opt = userRepo.findById(id);
        if (!opt.isPresent()) {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }

        UserEntity user = opt.get();
        user.setUserTask(task);
        userRepo.save(user);
        
        return new ResponseEntity<>("Task assigned to user ID: " + id, HttpStatus.OK);
    }
}
