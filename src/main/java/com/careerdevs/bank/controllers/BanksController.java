package com.careerdevs.bank.controllers;

import com.careerdevs.bank.models.Bank;
import com.careerdevs.bank.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/banks")
public class BanksController {

    @Autowired
    private BankRepository bankRepository;

    @PostMapping
    public ResponseEntity<?> addOneBankToDB(@RequestBody Bank newBankData) {
        try {
            // validation occurs here
            Bank addedBank = bankRepository.save(newBankData);
            // return ResponseEntity.ok(addedBank);//200 error code - success
            return new ResponseEntity<>(addedBank, HttpStatus.CREATED); // 201 error code
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage()); // http status code of 500
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllBanksFromDB() {
            List<Bank> allBanks = bankRepository.findAll();
            return new ResponseEntity<>(allBanks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBankByID(@PathVariable Long id) {
            Optional<Bank> requestBank = bankRepository.findById(id);
            if (requestBank.isEmpty()) {
                return new ResponseEntity<>("Bank not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(requestBank.get(), HttpStatus.OK);
    }
}