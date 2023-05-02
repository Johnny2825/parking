package ru.example.micro.parking.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
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
public class ParkingSpaceResponse {
    @EqualsAndHashCode.Exclude
    private Long id;
    @NotNull
    private Long parkingId;
    private Boolean reservationAvailable;
    private String placeCode;
    private Boolean isEmpty;
    private Long userId;
    private Long minutes;
    private BigDecimal payment;
}
