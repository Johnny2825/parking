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

                .build();
    }

    @Override
    public UserEntity map(UserDto userDto) {
        if (isNull(userDto)) {
            return null;
        }
        return UserEntity.builder()

                .build();
    }
}
