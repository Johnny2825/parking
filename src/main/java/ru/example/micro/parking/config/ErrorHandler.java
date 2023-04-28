package ru.example.micro.parking.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.example.micro.parking.exception.AbstractBusinessException;
import ru.example.micro.parking.model.dto.ErrorResponse;

import java.util.List;

import static ru.example.micro.parking.model.Constant.StatusCode.NOT_VALID_DATA;

/**
 * @author Tarkhov Evgeniy
 */
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({AbstractBusinessException.class})
    public ResponseEntity<ErrorResponse> handleException(AbstractBusinessException e) {
        ErrorResponse response = new ErrorResponse(e.getCode(), e.getMessage());
        return new ResponseEntity<>(response, new HttpHeaders(), e.getHttpStatus());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        ErrorResponse response = new ErrorResponse(NOT_VALID_DATA, errors.toString());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
