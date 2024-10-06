package com.assignment.services;

import com.assignment.dtos.CustomerDTO;
import com.assignment.entities.Account;
import com.assignment.entities.Customers;
import com.assignment.exceptions.UserNotFoundException;
import com.assignment.utils.Utility;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)

class ServiceImplTest {
    @InjectMocks
    private ServiceImpl service;
    @Mock
    private HazelcastInstance hazelcastInstance;
    @Mock
    private DiscoveryClient discoveryClient;
    @Mock
    private Utility utility;
    @Mock
    private WebClient webClient;

    @Test
    void createAccount() {
        BigDecimal initailAmt= new BigDecimal(10);
        IMap<Object, Object> mockCust=mock(IMap.class);
        List<ServiceInstance> mockInstance=mock(ArrayList.class);
        Customers mockCustomer=mock(Customers.class);
        Account mockAccount=mock(Account.class);
        Mockito.when(hazelcastInstance.getMap(anyString())).thenReturn(mockCust);
        when(mockCust.get(123L)).thenReturn(mockCustomer);
        when(discoveryClient.getInstances(anyString())).thenReturn(mockInstance);
       when(utility.
               fetchProxyDetails(discoveryClient,
                       "test","test","test","test")).thenReturn(webClient);
        ResponseEntity<String> result= service.createAccount(123L,initailAmt,"0009");
        assertEquals(result.getStatusCode().value(),201);
    }
    @Test
    void createAccountException() {
        BigDecimal initailAmt= new BigDecimal(10);
        IMap<Object, Object> mockCust=mock(IMap.class);
        Mockito.when(hazelcastInstance.getMap(anyString())).thenReturn(mockCust);
        assertThrows(UserNotFoundException.class, () -> {service.createAccount(123L,initailAmt,"0009");});

    }

    @Test
    void getUserAccounts() {
        IMap<Object, Object> mockCust=mock(IMap.class);
        Customers mockCustomer=mock(Customers.class);
        List<Account> mockAcc=mock(ArrayList.class);
        Mockito.when(hazelcastInstance.getMap(anyString())).thenReturn(mockCust);
        when(mockCust.get(123L)).thenReturn(mockCustomer);
        ResponseEntity<CustomerDTO> mockDto= service.getUserAccounts(123L);
        assertEquals(mockDto.getBody().accountList().size(),0);
    }
}