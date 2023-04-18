package ru.example.micro.parking.service.user;

import ru.example.micro.parking.controller.dto.UserDto;
import ru.example.micro.parking.entity.UserEntity;

/**
 * @author Tarkhov Evgeniy
 */
public class UserTestData {

    public UserEntity getUserEntity() {
        return UserEntity.builder()
                .id(1L)
                .firstName("Bob")
                .lastName("Smith")
                .phone("435-543-5")
                .email("bob@mail.com")
                .build();
    }

    public UserDto getUserDtoForCreate() {
        return UserDto.builder()
                .firstName("Bob")
                .lastName("Smith")
                .phone("435-543-5")
                .email("bob@mail.com")
                .build();
    }

    public UserDto getUserDto() {
        return UserDto.builder()
                .id(1L)
                .firstName("Bob")
                .lastName("Smith")
                .phone("435-543-5")
                .email("bob@mail.com")
                .build();
    }
}
