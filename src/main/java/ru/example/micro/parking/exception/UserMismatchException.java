package ru.example.micro.parking.exception;

import org.springframework.http.HttpStatus;

import static ru.example.micro.parking.model.Constant.StatusCode.USER_MISMATCH;

/**
 * @author Tarkhov Evgeniy
 */
public class UserMismatchException extends AbstractBusinessException {

    private static final String FORMAT = "Не совпадает id пользователя. Входной: %s; Существущий: %s ";

    public UserMismatchException(Long userIdInput, Long userIdExist) {
        super(USER_MISMATCH, String.format(FORMAT, userIdInput, userIdExist), HttpStatus.BAD_REQUEST);
    }
}
