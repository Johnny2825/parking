package ru.example.micro.parking.exception;

/**
 * @author Tarkhov Evgeniy
 */
public class ParkingSpaceIsNotEmpty extends RuntimeException {

    public ParkingSpaceIsNotEmpty(String message) {
        super(message);
    }
}
