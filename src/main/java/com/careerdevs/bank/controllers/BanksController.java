package com.careerdevs.bank.controllers;

import com.careerdevs.bank.models.Bank;
import com.careerdevs.bank.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/")
    public ResponseEntity<?> getAllBanksFromDB() {
            List<Bank> allBanks = bankRepository.findAll();
            // other options
            //return new ResponseEntity<>(allBanks, HttpStatus.OK);
            //return ResponseEntity.ok(bankRepository.findAll());//200 - success
            return new ResponseEntity<>(allBanks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBankByID(@PathVariable Long id) {
            //Bank requestedBank = bankRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); - use it instead of try catch, autosends 404;
            Optional<Bank> requestBank = bankRepository.findById(id);
            if (requestBank.isEmpty()) {
                return new ResponseEntity<>("Bank not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(requestBank.get(), HttpStatus.OK);
    }

    // Option 2:
    // Optional<Bank> requestedBank = bankRepositoryFindById(id);
    // if (requestedBank.isEmpty()) {return new ResponseEntity<>("Invalid ID", HttpStatus.NOT_FOUND);}
    // Line below will not run if conditional above is true, break occurs after return above
    // return new ResponseEntity<>(requestedBank.get(), HttpStatus.OK);

    @PostMapping("/{id}")
    public ResponseEntity<?> postOneBankByID(@PathVariable Long id, Bank newBankData) {
        Bank requestedBank = bankRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // option 1
//        return ResponseEntity.ok(bankRepository.save(newBankData));
// newBankData must contain ALL fields even if you aren't changing them

        // option 2
        // allows us to only change data provided to us
        // validates data at the same time and only allow changes to data if we allow them
        if (!newBankData.getName().equals("")) { // if key exists
            requestedBank.setName(newBankData.getName());
        }
        if (newBankData.getPhoneNumber() != null && newBankData.getPhoneNumber().length()>=3) { // if key doesn't exist
            requestedBank.setPhoneNumber(newBankData.getPhoneNumber());
        }
        return ResponseEntity.ok(bankRepository.save(requestedBank));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOneBankById(@PathVariable Long id) {
        Bank requestedBank = bankRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bankRepository.deleteById(id);
        return ResponseEntity.ok(requestedBank);
    }

    // custom query
    @GetMapping("/name/{name}")
    public ResponseEntity<?> findOneBankByName(@PathVariable String name) {
        Bank requestedBank = bankRepository.findByName(name).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<>(requestedBank, HttpStatus.OK);
    }

    @GetMapping("/areaCode/{areaCode}")
    public ResponseEntity<?> findAllBanksByAreaCode(@PathVariable String areaCode) {
        return new ResponseEntity<>(bankRepository.findAllAreaCodes(areaCode), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> findBankByCustomerId(@PathVariable Long id){
        Bank foundBank = bankRepository.getByCustomers_id(id).orElseThrow(()-> new ResponseStatusException((HttpStatus.NOT_FOUND)));
        return new ResponseEntity<>(foundBank, HttpStatus.OK);
    }
}