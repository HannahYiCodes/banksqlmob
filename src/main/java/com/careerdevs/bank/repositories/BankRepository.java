package com.careerdevs.bank.repositories;

import com.careerdevs.bank.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository <Bank, Long> {
    Optional<Bank> findByName(String name);

    @Query ("SELECT * FROM bank WHERE phone_number LIKE \"?1%\";")
        List<Bank> findAllAreaCodes(String areaCode);
}
