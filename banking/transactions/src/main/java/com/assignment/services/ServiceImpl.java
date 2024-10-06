package com.assignment.services;

import com.assignment.constants.TransferType;
import com.assignment.entities.Account;
import com.assignment.entities.Transaction;
import com.assignment.exceptions.AccountNotFoundException;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.random.RandomGenerator;

import static com.assignment.constants.TransactionConstants.*;

@Service
public class ServiceImpl implements  Services {
    private  final HazelcastInstance hazelcastInstance;
    public ServiceImpl( HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }


    @Override
    public ResponseEntity<String> insertTransaction(Account account, BigDecimal transferAmount) {
        IMap<Long,Account> accountIMap=hazelcastInstance.getMap(ACCOUNTS);
        IMap<Long, Transaction> transactionIMap=hazelcastInstance.getMap(TRANSACTIONS);
        Optional.ofNullable(accountIMap.get(account.getAccountId())).orElseThrow(()-> new AccountNotFoundException(String.format("Account with id %s not found", account.getAccountId())));
        RandomGenerator randomGenerator=RandomGenerator.getDefault();
        Transaction newTransaction =new Transaction();
        newTransaction.setTransactionId(Long.valueOf(randomGenerator.nextInt(Integer.MAX_VALUE)));
        newTransaction.setTime(LocalDateTime.now());
        newTransaction.setTransferAmount(transferAmount);
        if(transferAmount.signum()>0)
        {
            newTransaction.setType(TransferType.CREDIT);
        }
        else
        {
            newTransaction.setType(TransferType.DEBIT);
        }
        try {
            accountIMap.lock(account.getAccountId());
            transactionIMap.put(newTransaction.getTransactionId(), newTransaction);
            account.getTransactions().add(newTransaction);
            accountIMap.put(account.getAccountId(), account);
        }
        finally {
            {
                accountIMap.unlock(account.getAccountId());
            }
        }
        String body=String.format("Transaction entry created %s for the account number %s",newTransaction.getTransactionId(),account.getAccountId());
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }
}
