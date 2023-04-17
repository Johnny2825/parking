package ru.example.micro.parking.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timeFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timeTo;
    private BigDecimal payment;
}
