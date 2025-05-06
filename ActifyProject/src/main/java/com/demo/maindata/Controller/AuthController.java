package com.demo.maindata.Controller;

import java.util.Base64.Decoder;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.maindata.Component.JwtUtil;
import com.demo.maindata.DTO.LoginRequest;
import com.demo.maindata.DTO.RegisterReflection;
import com.demo.maindata.Repository.RoleRepository;
import com.demo.maindata.Repository.UserRepository;
import com.demo.maindata.entity.RoleEntity;
import com.demo.maindata.entity.RoleName;
import com.demo.maindata.entity.UserEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
    private UserRepository userRepo;
	@Autowired
    private RoleRepository roleRepo;
	@Autowired
    private PasswordEncoder encoder;
	@Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterReflection request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        RoleEntity role = roleRepo.findByName(RoleName.USER)
        		.orElseThrow(() -> new RuntimeException("Role USER not found in database"));
        user.getRoles().add(role);
        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
    	
    	Optional<UserEntity>  opt = userRepo.findByEmail(request.getEmail());
    	UserEntity userdata = opt.get();
    	if(userdata.getEmail().equals(request.getEmail())  && 
    		encoder.matches(request.getPassword(), userdata.getPassword()))
    	{
          String token = jwtUtil.generateToken(request.getEmail());
          return ResponseEntity.ok(token);
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    	}
    }

}
