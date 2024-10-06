package com.assignment.config;

import com.assignment.entities.Customers;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataInitializeTest {
    @InjectMocks
    private DataInitialize dataInitialize;
    @Mock
    private HazelcastInstance hazelcastInstance;

    @Test
    void initialzeCustomers() throws IOException {
        IMap<Object, Object> mockCust=mock(IMap.class);
        Mockito.when(hazelcastInstance.getMap(anyString())).thenReturn(mockCust);
        dataInitialize.initialzeCustomers();

    }
}