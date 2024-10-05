package com.assignment.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Customers {
    @JsonProperty("customerId")
    private  Long customerId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("surName")
    private String surName;
    private List<Account> accounts= new ArrayList<>();

}
