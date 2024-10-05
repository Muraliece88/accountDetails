package com.assignment.exceptions;

public class UserNotFoundException extends  RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
