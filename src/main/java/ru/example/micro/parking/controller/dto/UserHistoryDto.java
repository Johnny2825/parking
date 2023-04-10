package ru.example.micro.parking.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
public class UserHistoryDto {
    @EqualsAndHashCode.Exclude
    private Long id;
    private Long parkingSpaceId;
    private Long userId;
    private ZonedDateTime timeFrom;
    private ZonedDateTime timeTo;
    private BigDecimal payment;
}
