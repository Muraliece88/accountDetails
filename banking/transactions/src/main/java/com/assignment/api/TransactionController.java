package com.assignment.api;



import com.assignment.entities.Account;
import com.assignment.services.ServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

import static com.assignment.constants.TransactionConstants.CORRELATION_ID;

@Slf4j
@RequestMapping("/api/v1/transactions")
@RestController
public class TransactionController {
    private  final ServiceImpl serviceImpl;

    public TransactionController(ServiceImpl serviceImpl) {
        this.serviceImpl = serviceImpl;
    }
    @PostMapping("/create")
    public ResponseEntity<String> saveTransaction(
            @RequestBody @Valid Account account , @Valid @RequestParam BigDecimal amount)
    {
        String traceId= Optional.ofNullable(MDC.get(CORRELATION_ID)).get();
        log.info("Request has been received to log a transaction entry for trace: {}"+ traceId);
        return serviceImpl.insertTransaction(account, amount);
    }

}
