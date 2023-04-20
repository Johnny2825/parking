package ru.example.micro.parking.controller.dto;

import lombok.Data;

/**
 * @author Tarkhov Evgeniy
 */
@Data
public class ParkingSpaceUserDto {
    private Long parkingSpaceId;
    private Long userId;
}
