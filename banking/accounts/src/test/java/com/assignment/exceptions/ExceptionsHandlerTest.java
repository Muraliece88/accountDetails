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
        Exception transException= Mockito.mock(TransactionException.class);
        Exception uriNotFoundException= Mockito.mock(URINotFoundException.class);
        Exception userNotFoundException= Mockito.mock(UserNotFoundException.class);
        ResponseEntity<Errors> result1= handler.handleException(accountNotFoundException);
        ResponseEntity<Errors> result2= handler.handleException(transException);
        ResponseEntity<Errors> result3= handler.handleException(uriNotFoundException);
        ResponseEntity<Errors> result4= handler.handleException(userNotFoundException);
        assertEquals(result1.getStatusCode().value(),404);
        assertEquals(result2.getStatusCode().value(),500);
        assertEquals(result3.getStatusCode().value(),404);
        assertEquals(result4.getStatusCode().value(),404);

    }
}