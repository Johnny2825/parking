package ru.example.micro.parking.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
public class ParkingResponse {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;
    private String address;
    private Integer levelTotalAmount;
    private Integer parkingSpaceTotalAmount;
}
