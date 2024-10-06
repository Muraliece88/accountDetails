package com.assignment.config;

import com.assignment.entities.Customers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.assignment.constants.AccountConstants.CUSTOMERS;


@Slf4j
@Component
public class DataInitialize {
    private  final HazelcastInstance hazelcastInstance;
    public DataInitialize( HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }
    @PostConstruct
    public  void initialzeCustomers() throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        ClassPathResource resource=new ClassPathResource("data.json");
        InputStream inputStream= resource.getInputStream();
        JsonNode root= objectMapper.readTree(inputStream);
        List<Customers> customersList=objectMapper.readValue(root.get(CUSTOMERS).toString(),
               new TypeReference<List<Customers>>() {
               });
        IMap<Long,Customers> customersIMap=hazelcastInstance.getMap(CUSTOMERS);
        for(Customers customers: customersList)
        {
            customersIMap.put(customers.getCustomerId(), customers);
        }
        log.debug("Initial customer data has been loaded to cache");


    }
}
