package com.workintech.s18d4.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AddressException extends RuntimeException {

    private final HttpStatus httpStatus;

    public AddressException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
