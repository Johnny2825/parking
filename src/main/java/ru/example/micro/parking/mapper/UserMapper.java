package ru.example.micro.parking.mapper;

import ru.example.micro.parking.controller.dto.UserDto;
import ru.example.micro.parking.entity.UserEntity;


/**
 * @author Tarkhov Evgeniy
 */
public interface UserMapper {
    UserDto map(UserEntity userEntity);
    UserEntity map(UserDto userDto);
}
