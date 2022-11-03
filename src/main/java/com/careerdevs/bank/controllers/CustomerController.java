package com.careerdevs.bank.controllers;

import com.careerdevs.bank.models.Bank;
import com.careerdevs.bank.models.Customer;
import com.careerdevs.bank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer) {
        Customer customer = customerRepository.save(newCustomer);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
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

