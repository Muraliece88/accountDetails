
package com.assignment.services;

import com.assignment.dtos.CustomerDTO;
import com.assignment.entities.Account;
import com.assignment.entities.Customers;
import com.assignment.exceptions.UserNotFoundException;
import com.assignment.mappers.CustomerMapper;
import com.assignment.utils.Utility;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.*;
import java.util.random.RandomGenerator;

import static com.assignment.constants.AccountConstants.ACCOUNT;
import static com.assignment.constants.AccountConstants.CUSTOMERS;


@Service
@Slf4j
public class ServiceImpl implements  Services {
    private  final HazelcastInstance hazelcastInstance;
    public final String accountType="Current Account";
    private final DiscoveryClient discoveryClient;
    private  final Utility utility;
    @Value("${transaction.api.path}")
    private String apiPath;
    @Value("${transaction.api.endpoint}")
    private String apiEndpoint;
    @Value("${transaction.api.name}")
    private String apiName;
    @Value("${api.user.name}")
    private String apiUser;
    @Value("${api.user.password}")
    private String apiPass;
    private final CustomerMapper mappers = Mappers.getMapper(CustomerMapper.class);


    public ServiceImpl(DiscoveryClient discoveryClient, HazelcastInstance hazelcastInstance, Utility utility) {
        this.discoveryClient = discoveryClient;
        this.hazelcastInstance=hazelcastInstance;
        this.utility = utility;
    }

    /**
     * Opens a new account for an existing customer
     *
     * @param customerId
     * @param initialCredit
     * @param traceId
     * @return
     */

    @Override
    public ResponseEntity<String> createAccount(Long customerId, BigDecimal initialCredit,String traceId) {
        IMap<Long,Customers> customersIMap=hazelcastInstance.getMap("customers");
        IMap<Long,Account> accountIMap=hazelcastInstance.getMap("account");
        Customers customer=Optional.ofNullable(customersIMap.get(customerId)).orElseThrow(()-> new UserNotFoundException(String.format("Customer with id %s not found", customerId)));
        RandomGenerator randomGenerator=RandomGenerator.getDefault();
        Account account=new Account();
        account.setAccountId(Long.valueOf(randomGenerator.nextInt(Integer.MAX_VALUE)));
        account.setBalance(initialCredit);
        account.setAccountType(accountType);
        customer.getAccounts().add(account);
        accountIMap.put(account.getAccountId(),account);
        if (initialCredit.compareTo(BigDecimal.ZERO) > 0) {
            WebClient client= utility.fetchProxyDetails(discoveryClient, apiName, apiUser, apiPass, apiPath);
            utility.createTransaction(account,initialCredit,client,apiEndpoint,traceId);
            }
            customersIMap.put(customerId,customer);
            String body = String.format("New Account %s is opened for the customer %s", account.getAccountId(), customerId);
            return new ResponseEntity<>(body, HttpStatus.CREATED);

    }

    /**
     * Fetches the user information from cache maps
     * @param customerId
     * @return
     */
    @Override
    public ResponseEntity<CustomerDTO> getUserAccounts(Long customerId)
    {
        IMap<Long,Customers> customersIMap=hazelcastInstance.getMap(CUSTOMERS);
        IMap<Long,Account> accountIMap=hazelcastInstance.getMap(ACCOUNT);
        Customers customerDetails= Optional.ofNullable(customersIMap.get(customerId))
                .orElseThrow(()-> new UserNotFoundException
                        (String.format("Customer with id %s not found", customerId)));
        customerDetails.getAccounts().forEach(account ->
            account.setTransactions(accountIMap.get(account.getAccountId()).getTransactions()));
        return new ResponseEntity<> (mappers.INSTANCE.getCustomerResponse(customerDetails),HttpStatus.OK);

    }
}

