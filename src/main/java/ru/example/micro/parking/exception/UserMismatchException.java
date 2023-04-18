package ru.example.micro.parking.exception;

/**
 * @author Tarkhov Evgeniy
 */
public class UserMismatchException extends RuntimeException {
    public UserMismatchException(String message) {
        super(message);
    }
}
