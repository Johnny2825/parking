package ru.example.micro.parking.mapper;

import org.springframework.stereotype.Component;
import ru.example.micro.parking.controller.dto.UserDto;
import ru.example.micro.parking.entity.UserEntity;

import static java.util.Objects.isNull;

/**
 * @author Tarkhov Evgeniy
 */
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto map(UserEntity userEntity) {
        if (isNull(userEntity)) {
            return null;
        }
        return UserDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .build();
    }

    @Override
    public UserEntity map(UserDto userDto) {
        if (isNull(userDto)) {
            return null;
        }
        return UserEntity.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .build();
    }
}
