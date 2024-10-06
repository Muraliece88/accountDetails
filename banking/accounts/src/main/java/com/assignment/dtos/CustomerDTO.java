package com.assignment.dtos;
import java.util.List;

public record CustomerDTO(String name,
                          String surName,
                          List<AccountDTO> accountList)
{

}
