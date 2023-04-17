package ru.example.micro.parking.exception;

/**
 * @author Tarkhov Evgeniy
 */
public class UserExistException extends RuntimeException {

    public UserExistException(String message) {
        super(message);
    }
}
