package com.assignment.dtos;

import com.assignment.constants.TransferType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountDTOTest {

    @Test
    void testAccountDto()
    {
        List<TransactionDTO> transactionDTOList=new ArrayList<>();
        TransactionDTO transactionDTO=new TransactionDTO(new BigDecimal(10), LocalDateTime.now(), TransferType.CREDIT);
        transactionDTOList.add(transactionDTO);
        AccountDTO accountDTO=new AccountDTO(1L, new BigDecimal(10),"Current Savings",transactionDTOList);
        assertEquals(accountDTO.accountId(),1L);
    }

}