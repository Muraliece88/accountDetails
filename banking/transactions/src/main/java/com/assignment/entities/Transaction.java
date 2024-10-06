package com.assignment.entities;


import com.assignment.constants.TransferType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Transaction {
    private Long transactionId;
    private BigDecimal transferAmount;
    private LocalDateTime time;
    private TransferType type;
}
