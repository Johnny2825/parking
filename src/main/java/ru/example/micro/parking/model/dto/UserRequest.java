package ru.example.micro.parking.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
public class UserRequest {
    @NotBlank(message = "Поле имя не может быть пустым")
    private String firstName;
    @NotBlank(message = "Поле фамилия не может быть пустым")
    private String lastName;
    @NotBlank(message = "Поле телефон не может быть пустым")
    private String phone;
    @Email(message = "Должна быть указана почта")
    private String email;
}
