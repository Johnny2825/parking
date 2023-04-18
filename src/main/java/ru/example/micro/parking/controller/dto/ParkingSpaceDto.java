package ru.example.micro.parking.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull
    @Positive
    @EqualsAndHashCode.Exclude
    private Long id;
    @NotNull
    @Positive
    private Long parkingId;
    private String placeCode;
    private Boolean isEmpty;
    @NotNull
    @Positive
    private Long userId;
    private Long minutes;
    private BigDecimal payment;
}
