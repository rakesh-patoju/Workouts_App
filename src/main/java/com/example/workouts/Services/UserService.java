package com.example.workouts.Services;

import com.example.workouts.Entities.User;
import com.example.workouts.Repositaries.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registration(User user){
        user.setOriginalPassword(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean usernameExists(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }


}
