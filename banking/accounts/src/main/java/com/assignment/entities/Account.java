package com.assignment.entities;


import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Account {
    private Long accountId;
    private BigDecimal balance;
    private String accountType;
    private List<Transaction> transactions= new ArrayList<>();


}
