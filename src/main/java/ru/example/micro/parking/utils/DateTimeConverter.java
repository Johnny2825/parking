package ru.example.micro.parking.utils;

import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

import static ru.example.micro.parking.model.Constant.FORMATTER;

/**
 * @author Tarkhov Evgeniy
 */
public class DateTimeConverter {
    private DateTimeConverter() {
    }

    public static String localDateTimeToString(@NonNull LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }
}
