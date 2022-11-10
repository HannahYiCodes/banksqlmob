package com.careerdevs.bank.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location; // use address or geolocation instead
    private String phoneNumber;

    @OneToMany(mappedBy = "bank", fetch = FetchType.LAZY) //If issues. change lazy to eager
//    @JsonIncludeProperties({"firstName", "lastName", "id"})
    @JsonIgnoreProperties({"email", "age", "location", "bank"}) //show everything but this, blacklixt
    private List<Customer> customers;

    // default constructor
    public Bank() {

    }

    public Bank(String name, String location, String phoneNumber) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAreaCode() {
        return phoneNumber.substring(0, 3);
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
