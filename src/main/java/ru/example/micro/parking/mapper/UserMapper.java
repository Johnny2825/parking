package ru.example.micro.parking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.example.micro.parking.entity.UserEntity;
import ru.example.micro.parking.model.dto.UserRequest;
import ru.example.micro.parking.model.dto.UserResponse;

/**
 * @author Tarkhov Evgeniy
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponse toResponse(UserEntity userEntity);

    UserEntity toEntity(UserRequest userRequest);
}
