package com.example.rest.service.exceptions;

import org.springframework.http.HttpStatus;

public abstract class BaseBuzinessException extends RuntimeException{

    public BaseBuzinessException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatusCode();


}
