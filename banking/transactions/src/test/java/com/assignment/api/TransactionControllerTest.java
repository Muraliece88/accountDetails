package com.assignment.api;

import com.assignment.constants.TransferType;
import com.assignment.entities.Account;
import com.assignment.entities.Customers;
import com.assignment.entities.Transaction;
import com.assignment.services.ServiceImpl;
import com.google.gson.Gson;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.assignment.constants.TransactionConstants.ACCOUNT;
import static com.assignment.constants.TransactionConstants.TRANSACTIONS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TransactionControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    private  MockedStatic mockMdc;
    @Autowired
    private ServiceImpl serviceImpl;
    private HazelcastInstance hazelcastInstance;
    private IMap<Long,Account> accountIMap;
    private IMap<Long, Transaction> transactionIMap;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Account account=new Account();
        account.setBalance(new BigDecimal(10));
        account.setAccountId(1L);
        account.setAccountType("Current account");
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        hazelcastInstance= Hazelcast.newHazelcastInstance();
        accountIMap=hazelcastInstance.getMap(ACCOUNT);
        transactionIMap=hazelcastInstance.getMap(TRANSACTIONS);
        accountIMap.put(account.getAccountId(),account);

        mockMdc=mockStatic(MDC.class);

    }

    @Test
    void saveTransaction() throws Exception {
        mockMdc.when(()->MDC.get(anyString())).thenReturn("mdc");
        String json ="{\n" +
                "    \"accountId\" : \"1\",\n" +
                "    \"balance\" : \"10\",\n" +
                "    \"accountType\": \"Current Savings\"\n" +
                "}";
        mockMvc.perform(post("/api/v1/transactions/create",123L)
                        .param("amount","10")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }
}