package ru.example.micro.parking.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
public class ParkingSpaceDto {
    @EqualsAndHashCode.Exclude
    private Long id;
    private Long parkingId;
    private String placeCode;
    private Long userId;
    private Integer minutes;
}
