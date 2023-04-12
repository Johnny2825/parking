package ru.example.micro.parking.service.user;

import lombok.NonNull;
import ru.example.micro.parking.controller.dto.UserDto;

import java.util.Optional;

/**
 * @author Tarkhov Evgeniy
 */
public interface UserService {

    Optional<UserDto> findUserById(@NonNull Long userId);

    Optional<UserDto> createUser(@NonNull UserDto userDto);

    Optional<UserDto> updateUser(@NonNull Long userId, UserDto userDto);

    Optional<UserDto> deleteUser(@NonNull Long userId);

}
