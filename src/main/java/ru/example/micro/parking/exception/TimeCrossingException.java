package ru.example.micro.parking.exception;

import org.springframework.http.HttpStatus;

import static ru.example.micro.parking.model.Constant.StatusCode.TIME_CROSSING;

/**
 * @author Tarkhov Evgeniy
 */
public class TimeCrossingException extends AbstractBusinessException {

    private static final String FORMAT = "Невозможно забронировать на период %s - %s. Пересечение с существующим диапазоном времени";

    public TimeCrossingException(String timeFrom, String timeTo) {
        super(TIME_CROSSING, String.format(FORMAT, timeFrom, timeTo), HttpStatus.BAD_REQUEST);
    }
}
