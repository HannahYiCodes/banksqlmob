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

// TODO: REDO ALL VIDEOS :D
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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User authUser) {
        // TODO:
        // Find the user
        // Compare password provided with password of user account
        // Create random token & save to user record
        // Return login token

        Optional<User> foundUser = userRepository.findById(authUser.getUsername());
        if (!authUser.getPassword().equals(foundUser.get().getPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
//        Random randomNum = new Random();
        int randomNum = ThreadLocalRandom.current().nextInt();  // 2 users could have same token, one possibility to avoid this is use current day/time     OR
        // concatenate username to end of generated String
        authUser.setLoginToken(Integer.toString(randomNum) + foundUser.get().getUsername());
        userRepository.save(authUser);
        return new ResponseEntity<>(authUser.getLoginToken(), HttpStatus.OK);
    }
}
