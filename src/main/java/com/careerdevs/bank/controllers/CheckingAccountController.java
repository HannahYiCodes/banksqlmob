package com.careerdevs.bank.controllers;

import com.careerdevs.bank.models.CheckingAccount;
import com.careerdevs.bank.models.Customer;
import com.careerdevs.bank.repositories.CheckingAccountRepository;
import com.careerdevs.bank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/checking")
public class CheckingAccountController {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/customer/{id}")
    public ResponseEntity<?> addCheckingAccountToDB(@RequestBody CheckingAccount newCheckingAccountData, @PathVariable Long id) {
        // find the customer in the customer database
        // return bad request if no customer
        // add the customer record to the newCheckingAccountData object
        // save it

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        newCheckingAccountData.getCustomers().add(customer);
        CheckingAccount addedCheckingAccount = checkingAccountRepository.save(newCheckingAccountData);
        return new ResponseEntity<>(addedCheckingAccount, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllCheckingAccounts() {
        List<CheckingAccount> allCheckingAccounts = checkingAccountRepository.findAll();
        return new ResponseEntity<>(checkingAccountRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCheckingAccountById(@PathVariable Long id) {
        CheckingAccount requestedCheckingAccounts = checkingAccountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(requestedCheckingAccounts, HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateCheckingAccountById(@PathVariable Long id, @RequestBody CheckingAccount newCheckingAccountData) {
        CheckingAccount requestedCheckingAccount = checkingAccountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!newCheckingAccountData.getAlias().equals("")) {
            requestedCheckingAccount.setAlias(newCheckingAccountData.getAlias());
        }
        return ResponseEntity.ok(checkingAccountRepository.save(requestedCheckingAccount));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCheckingAccountById(@PathVariable Long id) {
        CheckingAccount requestedCheckingAccount = checkingAccountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        checkingAccountRepository.delete(requestedCheckingAccount);
        checkingAccountRepository.deleteById(id);
        return ResponseEntity.ok(requestedCheckingAccount);
    }

    @GetMapping("/bank/{id}")
    public ResponseEntity<?> getAccountsByBankId(@PathVariable Long id) {
        return ResponseEntity.ok(checkingAccountRepository.findAllByCustomers_Bank_Id(id));
    }

    @PutMapping("/addCustomer/{cId}/{aId}")
    public ResponseEntity<?> addCustomerToAccount(@PathVariable Long cId, @PathVariable Long aId) {
        // find account or return 404
        // find customer or return 404
        // add customer to account's customer list
        // save
        CheckingAccount requestedCheckingAccount = checkingAccountRepository.findById(aId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Customer requestedCustomer = customerRepository.findById(cId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        requestedCheckingAccount.getCustomers().add(requestedCustomer);
        return ResponseEntity.ok(checkingAccountRepository.save(requestedCheckingAccount));

    }

}