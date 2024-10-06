package com.assignment.api;


import com.assignment.dtos.CustomerDTO;
import com.assignment.services.ServiceImpl;
import jakarta.validation.constraints.Digits;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RequestMapping("/api/v1/accounts")
@RestController
@Validated
public class AccountController {
    private  final ServiceImpl serviceImpl;

    public AccountController(ServiceImpl serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    /**
     *  API method to open new account
     * @param customerId
     * @param initialCredit
     * @return
     */

    @PostMapping("/open/{customerId}")
    public ResponseEntity<String> openAccount(@PathVariable Long customerId , @Digits(integer = 4, fraction = 2, message = "Initial credit can have 4 digits and 2 decimal place ") @RequestParam BigDecimal initialCredit)
    {
        return serviceImpl.createAccount(customerId, initialCredit,UUID.randomUUID().toString());
    }

    /**
     * API method to get customer account and its transactions
     * @param customerId
     * @return
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerDetails(@PathVariable Long customerId)
    {
        return serviceImpl.getUserAccounts(customerId);
    }
}
