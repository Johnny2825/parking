package ru.example.micro.parking.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
public class ParkingDto {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;
    private String address;
    private Integer parkingSpaceAmount;
}
