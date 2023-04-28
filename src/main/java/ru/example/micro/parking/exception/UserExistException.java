package ru.example.micro.parking.exception;

import org.springframework.http.HttpStatus;

import static ru.example.micro.parking.model.Constant.StatusCode.USER_EXIST;

/**
 * @author Tarkhov Evgeniy
 */
public class UserExistException extends AbstractBusinessException {

    private static final String FORMAT = "Пользователь с email адресом %s уже существует";

    public UserExistException(String email) {
        super(USER_EXIST, String.format(FORMAT, email), HttpStatus.BAD_REQUEST);
    }
}
