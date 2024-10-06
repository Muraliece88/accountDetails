package com.assignment.services;

import com.assignment.dtos.CustomerDTO;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface Services {
    ResponseEntity< String> createAccount(Long customerId, BigDecimal initialCredit,String traceId);
    ResponseEntity<CustomerDTO> getUserAccounts(Long customerId);
}
