package com.assignment.exceptions;

import jakarta.validation.ConstraintDefinitionException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice(basePackages = "com.assignment")
public class ExceptionsHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Errors> handleException(Exception ex) {

        HttpStatus status = eveluateExceptionKind(ex);
        log.error("Exception occured when processing the request:"+ex.getMessage());
        Errors errors=new Errors(status.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(status).body(errors);

    }

    private  HttpStatus eveluateExceptionKind(Exception ex)
    {
        if(ex instanceof AccountNotFoundException){
            return HttpStatus.NOT_FOUND;
        }
        else if(ex instanceof ConstraintViolationException){
            return HttpStatus.BAD_REQUEST;
        }
        else if(ex instanceof MissingServletRequestParameterException){
            return HttpStatus.BAD_REQUEST;
        }
        else if(ex instanceof ConstraintDefinitionException){
            return HttpStatus.BAD_REQUEST;
        }
        else if(ex instanceof MethodArgumentNotValidException){
            return HttpStatus.BAD_REQUEST;
        }
        else if(ex instanceof MethodArgumentTypeMismatchException){
            return HttpStatus.BAD_REQUEST;
        }
        else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
