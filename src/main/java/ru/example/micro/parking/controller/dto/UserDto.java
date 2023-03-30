package ru.example.micro.parking.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private Boolean active;
}
