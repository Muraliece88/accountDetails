package com.assignment.dtos;

import com.assignment.constants.TransferType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(BigDecimal amount, LocalDateTime time, TransferType transactionType) {
}
