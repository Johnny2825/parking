package ru.example.micro.parking.model.dto;

import lombok.Data;

/**
 * @author Tarkhov Evgeniy
 */
@Data
public class ParkingSpaceUserRequest {
    private Long parkingSpaceId;
    private Long userId;
}
