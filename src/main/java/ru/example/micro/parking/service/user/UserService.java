package ru.example.micro.parking.service.user;

import ru.example.micro.parking.controller.dto.UserDto;

import java.util.Optional;

/**
 * @author Tarkhov Evgeniy
 */
public interface UserService {

    Optional<UserDto> findUserById(Long userId);

    Optional<UserDto> createUser(UserDto userDto);

    Optional<UserDto> updateUser(Long userId, UserDto userDto);

    Optional<UserDto> deleteUser(Long userId);

}
