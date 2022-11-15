package com.careerdevs.bank.controllers;

import com.careerdevs.bank.models.User;
import com.careerdevs.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@CrossOrigin
@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> addOneUser(@RequestBody User newUserData) {
        try {
            User addedUser = userRepository.save(newUserData);
            return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


//    @GetMapping("/token/{loginToken}")
//    public ResponseEntity<?> getAllUsersByToken(@PathVariable String loginToken) {
//        User foundUser = userRepository.findByLoginToken(loginToken).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
//        );
//        return new ResponseEntity<>(foundUser, HttpStatus.OK);
//    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User authUser) {
        // Find the user
        // Compare password provided with password of user account
        // Create random token and save record
        // Return login token

        Optional<User> foundUser = userRepository.findById(authUser.getUsername());
        if (!authUser.getPassword().equals(foundUser.get().getPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        int randomNum = ThreadLocalRandom.current().nextInt();
        authUser.setLoginToken(Integer.toString(randomNum));
        userRepository.save(authUser);
        return new ResponseEntity<>(authUser.getLoginToken(), HttpStatus.OK);
    }
}
