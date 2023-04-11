package ru.example.micro.parking.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingSpaceDto {
    @EqualsAndHashCode.Exclude
    private Long id;
    private Long parkingId;
    private String placeCode;
    private Boolean isEmpty;
    private Long userId;
    private Long minutes;
    private BigDecimal payment;
}
