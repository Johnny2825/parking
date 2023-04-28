package ru.example.micro.parking.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
public class MailMessageOutput {
    private String address;
    private String message;
}
