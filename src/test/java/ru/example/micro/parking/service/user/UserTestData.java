package ru.example.micro.parking.service.user;

import ru.example.micro.parking.model.dto.UserRequest;
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

    public UserRequest getUserDtoForCreate() {
        return UserRequest.builder()
                .firstName("Bob")
                .lastName("Smith")
                .phone("435-543-5")
                .email("bob@mail.com")
                .build();
    }

    public UserRequest getUserDto() {
        return UserRequest.builder()
                .id(1L)
                .firstName("Bob")
                .lastName("Smith")
                .phone("435-543-5")
                .email("bob@mail.com")
                .build();
    }
}
