package com.order.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomException extends RuntimeException{
    private HttpStatus statusCode;
    public CustomException(String message, HttpStatus statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
