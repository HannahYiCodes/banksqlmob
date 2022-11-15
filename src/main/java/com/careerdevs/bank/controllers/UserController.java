package com.careerdevs.bank.controllers;

import com.careerdevs.bank.models.User;
import com.careerdevs.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginToken/{loginToken}")
    public ResponseEntity<?> getAllUserByLoginToken(@PathVariable String loginToken) {
        User requestedUser = userRepository.findById(loginToken).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(requestedUser, HttpStatus.OK);

    }
}
