package com.assignment.services;

import com.assignment.entities.Account;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceImplTest {
    @InjectMocks
    private  ServiceImpl service;
    @Mock
    private HazelcastInstance hazelcastInstance;

    @Test
    void insertTransaction() {
        Account account= new Account();
        account.setAccountType("Current Savings");
        account.setAccountId(1L);
        BigDecimal transferAmount =new BigDecimal(10) ;
        account.setBalance(transferAmount);
        IMap<Object, Object> mockCust=mock(IMap.class);
        Account mockAccount=mock(Account.class);
        Mockito.when(hazelcastInstance.getMap(anyString())).thenReturn(mockCust);
        when(mockCust.get(1L)).thenReturn(mockAccount);
        ResponseEntity<String> responseMock= service.insertTransaction(account,transferAmount);
        assertEquals(responseMock.getStatusCode().value(),201);
    }
}