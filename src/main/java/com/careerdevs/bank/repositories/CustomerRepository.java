package com.careerdevs.bank.repositories;

import com.careerdevs.bank.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByLastName(String lastName);

    List<Customer> findAllByBank_id(Long id);

    // find by user id
    Optional<Customer> findByUserId(String user);
}
