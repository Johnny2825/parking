package ru.example.micro.parking.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.ZonedDateTime;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
public class ParkingSpaceActivityDto {
    @EqualsAndHashCode.Exclude
    private Long id;
    private Long parkingSpaceId;
    private Long userId;
    private ZonedDateTime lastUpdate;
}
