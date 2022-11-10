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
    BankRepository bankRepository;

    @PostMapping("/{bankId}")
    public ResponseEntity<?> createCustomer(@RequestBody Customer newCustomerData, @PathVariable Long bankId) {
        // Find the bank by ID in the repository
        // If bank doesn't exist return bad request
        // If bank exist add to newCustomerData and save
        Bank requestedBank = bankRepository.findById(bankId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)); //user got us bad request or wrong data
        newCustomerData.setBank(requestedBank);
        Customer addedCustomer = customerRepository.save(newCustomerData);
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCustomersFromDB() {
        List<Customer> allCustomers = customerRepository.findAll();
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        Customer requestedCustomer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(requestedCustomer, HttpStatus.OK);
    }


    @PutMapping("/{id}") //put should never create
    public ResponseEntity<?> updateOneCustomerById(@PathVariable Long id, @RequestBody Customer newCustomerData) {
        Customer requestedCustomer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!newCustomerData.getFirstName().equals("")) {
            requestedCustomer.setFirstName(newCustomerData.getFirstName());
        }
        if (!newCustomerData.getLastName().equals("")) {
            requestedCustomer.setLastName(newCustomerData.getLastName());
        }
        if (!newCustomerData.getEmail().equals("")) {
            requestedCustomer.setEmail(newCustomerData.getEmail());
        }
        if (!newCustomerData.getAge().equals("")) {
            requestedCustomer.setAge(newCustomerData.getAge());
        }
        if (!newCustomerData.getLocation().equals("")) {
            requestedCustomer.setLocation(newCustomerData.getLocation());
        }


        return ResponseEntity.ok(customerRepository.save(requestedCustomer));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Customer requestedCustomer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        customerRepository.deleteById(id);
        return new ResponseEntity<>(requestedCustomer, HttpStatus.OK);

    }

    @GetMapping("/name/{lastName}")
    public ResponseEntity<?> getCustomersByLastName(@PathVariable String lastName){
        List<Customer> foundCustomers = customerRepository.findAllByLastName(lastName);
        return new ResponseEntity<>(foundCustomers, HttpStatus.OK);
    }


    @GetMapping("/bank/{bankId}")
    public ResponseEntity<?> getAllCustomersByBankId(@PathVariable Long bankId){
        List<Customer> allCustomers = customerRepository.findAllByBank_id(bankId);
        return  new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }
    }

