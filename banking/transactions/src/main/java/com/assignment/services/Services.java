package com.assignment.services;


import com.assignment.entities.Account;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;

public interface Services {
    ResponseEntity<String> insertTransaction(Account account , BigDecimal amount);

}
