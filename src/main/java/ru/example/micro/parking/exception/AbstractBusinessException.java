package ru.example.micro.parking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Tarkhov Evgeniy
 */
@Getter
public abstract class AbstractBusinessException extends RuntimeException {
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    public AbstractBusinessException(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
