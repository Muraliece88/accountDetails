package com.assignment.utils;

import com.assignment.entities.Account;
import com.assignment.services.ServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtilityTest {
    @InjectMocks
    private Utility utility;
    @Mock
    private  DiscoveryClient discoveryClient;
    @Mock
    private WebClient.RequestBodyUriSpec bodyUriSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;
    @Mock
    private WebClient.RequestBodySpec bodySpec;
    @Mock
    private WebClient.RequestHeadersSpec headerSpec;


    @Test
    void fetchProxyDetails() {
        ServiceInstance serviceInstance=mock(ServiceInstance.class);
        when(discoveryClient.getInstances(anyString())).thenReturn(List.of(serviceInstance));
        when(serviceInstance.getUri()).thenReturn(URI.create("http://localhost:8080"));
        WebClient mockClient= utility.fetchProxyDetails(discoveryClient,"test","testUser","testpass","/testbase");
        assertNotNull(mockClient);
    }

    @Test
    void createTransaction() {
        Account accountMock=mock(Account.class);
        BigDecimal mockCal=new BigDecimal(10);
        WebClient mockClient=mock(WebClient.class);
        Mono mockMono=mock(Mono.class);
        Mockito.when(mockClient.post()).thenReturn(bodyUriSpec);
        Mockito.when(bodyUriSpec.uri(Mockito.<Function<UriBuilder, URI>>any())).
                thenReturn(bodySpec);
        when(bodySpec.headers(any())).thenReturn(bodySpec);
        when(bodySpec.bodyValue(any())).thenReturn(headerSpec);
        when(headerSpec.exchangeToMono(any())).thenReturn(mockMono);
        when(mockMono.doOnError(any())).thenReturn(mockMono);
        when(bodySpec.accept(any())).thenReturn(bodySpec);
        utility.createTransaction(accountMock,mockCal,mockClient,"test","test");

    }
}