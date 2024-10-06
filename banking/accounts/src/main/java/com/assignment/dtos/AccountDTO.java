package com.assignment.dtos;

import java.math.BigDecimal;
import java.util.List;

public record AccountDTO(Long accountId,
                         BigDecimal balance,
                         String accountType,
                         List<TransactionDTO> transactions) {
}
