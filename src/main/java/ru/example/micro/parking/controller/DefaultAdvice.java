package ru.example.micro.parking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.example.micro.parking.controller.dto.ResponseDto;
import ru.example.micro.parking.exception.ParkingSpaceIsNotEmpty;
import ru.example.micro.parking.exception.ParkingSpaceNotFoundException;
import ru.example.micro.parking.exception.TimeException;
import ru.example.micro.parking.exception.UserExistException;
import ru.example.micro.parking.exception.UserMismatchException;
import ru.example.micro.parking.exception.UserNotFoundException;

import java.time.LocalDateTime;

/**
 * @author Tarkhov Evgeniy
 */
@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler({ParkingSpaceNotFoundException.class,
            ParkingSpaceIsNotEmpty.class,
            TimeException.class,
            UserNotFoundException.class,
            UserExistException.class,
            UserMismatchException.class})
    public ResponseEntity<ResponseDto> handleException(RuntimeException e) {
        ResponseDto response = new ResponseDto(String.format("%s - %s", LocalDateTime.now(), e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
