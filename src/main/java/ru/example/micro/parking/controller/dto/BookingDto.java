package ru.example.micro.parking.controller.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Tarkhov Evgeniy
 */
@Data
@Builder
public class BookingDto {   //для резервирования
    private Long parkingId;
    private Long userId;
    private String placeCode;

//    private Long reservationParkingSpaceId;

}
