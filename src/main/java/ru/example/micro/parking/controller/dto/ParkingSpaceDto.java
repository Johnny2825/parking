package ru.example.micro.parking.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.ZonedDateTime;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
public class ParkingSpaceDto {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String code;
    private Boolean status;
//    private ZonedDateTime timeFrom;
//    private ZonedDateTime timeTo;
}
