package com.assignment.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class ExceptionsHandlerTest {

    @InjectMocks
    private ExceptionsHandler handler;
    @Test
    void handleException() {
        Exception accountNotFoundException= Mockito.mock(AccountNotFoundException.class);
        ResponseEntity<Errors> result1= handler.handleException(accountNotFoundException);
        assertEquals(result1.getStatusCode().value(),404);

    }
}