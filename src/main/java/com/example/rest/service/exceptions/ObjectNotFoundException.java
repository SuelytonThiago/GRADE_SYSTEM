package com.example.rest.service.exceptions;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends BaseBuzinessException{

    public ObjectNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
