package ru.example.micro.parking.exception;

import org.springframework.http.HttpStatus;

import static ru.example.micro.parking.model.Constant.StatusCode.USER_NOT_FOUND;

/**
 * @author Tarkhov Evgeniy
 */
public class UserNotFoundException extends AbstractBusinessException {

    private static final String FORMAT = "Не найден пользователь с идентификатором %s";

    public UserNotFoundException(Long userId) {
        super(USER_NOT_FOUND, String.format(FORMAT, userId), HttpStatus.BAD_REQUEST);
    }
}
