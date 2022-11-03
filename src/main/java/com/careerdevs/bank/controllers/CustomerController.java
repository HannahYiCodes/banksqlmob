package com.careerdevs.bank.controllers;

import com.careerdevs.bank.models.Bank;
import com.careerdevs.bank.models.Customer;
import com.careerdevs.bank.repositories.BankRepository;
import com.careerdevs.bank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankRepository bankRepository;

    @PostMapping("/{bankId}")
    public ResponseEntity<Customer> createOneCustomer(@RequestBody Customer newCustomerData, @PathVariable Long bankId) {
        // Find the bank by ID in the repository
        // If bank doesn't exist return bad request
        // If bank exist add to newCustomerData and save

        Bank newBank = bankRepository.findById(bankId).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        newCustomerData.setBank(requestedBank);

        Customer newCustomer = customerRepository.save(newCustomerData);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/allcustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> allCustomer = customerRepository.findAll();
        return new ResponseEntity<>(allCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/deletecustomer/{id}")
    public ResponseEntity<Customer> deleteById(@PathVariable("id") long id) {

    }
    }

