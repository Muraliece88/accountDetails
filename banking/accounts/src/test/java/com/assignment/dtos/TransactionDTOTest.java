package com.assignment.dtos;

import com.assignment.constants.TransferType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionDTOTest {

    @Test
    void testTransactionDto()
    {
        TransactionDTO transactionDTO=new TransactionDTO(new BigDecimal(10), LocalDateTime.now(), TransferType.CREDIT);
        assertNotNull(transactionDTO);

    }

}