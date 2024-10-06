package com.assignment.mappers;

import com.assignment.dtos.AccountDTO;
import com.assignment.dtos.CustomerDTO;
import com.assignment.dtos.TransactionDTO;
import com.assignment.entities.Account;
import com.assignment.entities.Customers;
import com.assignment.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "accounts",target = "accountList")
    CustomerDTO getCustomerResponse (Customers customer);
    @Mapping(source = "transactions",target = "transactions")
    @Mapping(source = "accountType",target = "accountType")
    AccountDTO getAccountResponse (Account accounts);
    @Mapping(source = "transferAmount",target = "amount")
    @Mapping(source = "type",target = "transactionType")
    TransactionDTO getTransactionResponse (Transaction transaction);


}
