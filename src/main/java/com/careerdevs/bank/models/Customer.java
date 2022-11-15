package com.careerdevs.bank.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import javax.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //jpa needs setters anyway

    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String location;

    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    @JsonIncludeProperties("id")
    private Bank bank; //we need getters and setters for jpa

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "username")
    @JsonIncludeProperties("username")
    private User user;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, Integer age, String location) { //data we expecting from the user
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // getters

    public Bank getBank() {
        return bank;
    }


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public String getLocation() {
        return location;
    }


    // setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

}
