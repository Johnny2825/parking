package ru.example.micro.parking.exception;

import org.springframework.http.HttpStatus;

import static ru.example.micro.parking.model.Constant.StatusCode.PARKING_SPACE_NOT_EMPTY;

/**
 * @author Tarkhov Evgeniy
 */
public class ParkingSpaceNotEmptyException extends AbstractBusinessException {

    private static final String FORMAT = "Парковочное место с идентификатором %s занято пользователем %s";

    public ParkingSpaceNotEmptyException(Long parkingSpaceId, Long userId) {
        super(PARKING_SPACE_NOT_EMPTY, String.format(FORMAT, parkingSpaceId, userId), HttpStatus.BAD_REQUEST);

    }
}
