package ru.example.micro.parking.service.business.user;

import lombok.NonNull;
import ru.example.micro.parking.model.dto.UserRequest;
import ru.example.micro.parking.model.dto.UserResponse;

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
    Optional<UserResponse> findUserById(@NonNull Long userId);

    /**
     * Создание пользователя
     * @param userRequest пользователь
     * @return созданный пользователь
     */
    Optional<UserResponse> createUser(@NonNull UserRequest userRequest);

    /**
     * Обновление пользователя
     * @param userId идентификатор пользователя
     * @param userRequest пользователь
     * @return обновленный пользователь
     */
    Optional<UserResponse> updateUser(@NonNull Long userId, UserRequest userRequest);

    /**
     * Удаление польлзователя
     * @param userId идентификатор пользователя
     * @return удаленный пользователь
     */
    Optional<UserResponse> deleteUser(@NonNull Long userId);

}
