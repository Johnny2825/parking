package ru.example.micro.parking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
}
