package ru.example.micro.parking.service.user;

import lombok.NonNull;
import ru.example.micro.parking.controller.dto.UserDto;

import java.util.Optional;

/**
 * Сервис по работе с пользователями
 *
 * @author Tarkhov Evgeniy
 */
public interface UserService {

    /**
     * Получения пользователя по идентификатору
     * @param userId идентификатор пользователя
     * @return объект пользователя
     */
    Optional<UserDto> findUserById(@NonNull Long userId);

    /**
     * Создание пользователя
     * @param userDto пользователь
     * @return созданный пользователь
     */
    Optional<UserDto> createUser(@NonNull UserDto userDto);

    /**
     * Обновление пользователя
     * @param userId идентификатор пользователя
     * @param userDto пользователь
     * @return обновленный пользователь
     */
    Optional<UserDto> updateUser(@NonNull Long userId, UserDto userDto);

    /**
     * Удаление польлзователя
     * @param userId идентификатор пользователя
     * @return удаленный пользователь
     */
    Optional<UserDto> deleteUser(@NonNull Long userId);

}
