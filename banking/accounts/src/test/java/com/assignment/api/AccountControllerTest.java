package com.assignment.api;

import com.assignment.entities.Account;
import com.assignment.entities.Customers;
import com.assignment.services.ServiceImpl;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static com.assignment.constants.AccountConstants.CUSTOMERS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    private HazelcastInstance hazelcastInstance;
    private IMap<Long, Customers> customerIMap;

    private final String getURL = "/api/v1/accounts/open/{customerId}";

    @BeforeEach
    void setUp() {
        Customers customers=new Customers();
        customers.setCustomerId(123L);
        customers.setName("test");
        customers.setSurName("me");
        hazelcastInstance= Hazelcast.newHazelcastInstance();
        customerIMap=hazelcastInstance.getMap(CUSTOMERS);
        customerIMap.put(customers.getCustomerId(),customers);
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    void openAccount() throws Exception {
        mockMvc.perform(post("/api/v1/accounts/open/{customerId}",123L)
                        .param("initialCredit","0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));

    }

    @Test
    void getCustomerDetails() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/customer/{customerId}",123L)

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}