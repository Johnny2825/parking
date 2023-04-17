package ru.example.micro.parking.exception;

/**
 * @author Tarkhov Evgeniy
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
