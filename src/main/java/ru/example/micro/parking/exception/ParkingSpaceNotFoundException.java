package ru.example.micro.parking.exception;

import org.springframework.http.HttpStatus;

import static ru.example.micro.parking.model.Constant.StatusCode.PARKING_SPACE_NOT_FOUND;

/**
 * @author Tarkhov Evgeniy
 */
public class ParkingSpaceNotFoundException extends AbstractBusinessException {

    private static final String FORMAT = "Парковочное место с идентификатором %s не найдено";

    public ParkingSpaceNotFoundException(Long parkingSpaceId) {
        super(PARKING_SPACE_NOT_FOUND, String.format(FORMAT, parkingSpaceId), HttpStatus.BAD_REQUEST);
    }
}
