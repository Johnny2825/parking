package ru.example.micro.parking.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
public class UserResponse {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
