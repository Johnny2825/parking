package ru.example.micro.parking.exception;

/**
 * @author Tarkhov Evgeniy
 */
public class ParkingSpaceNotFoundException extends RuntimeException {

    public ParkingSpaceNotFoundException(String message) {
        super(message);
    }
}
