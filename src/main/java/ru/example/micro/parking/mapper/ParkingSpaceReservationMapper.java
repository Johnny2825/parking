package ru.example.micro.parking.mapper;

import ru.example.micro.parking.controller.dto.ParkingSpaceReservationDto;
import ru.example.micro.parking.entity.ParkingSpaceReservationEntity;

/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingSpaceReservationMapper {

    ParkingSpaceReservationDto map(ParkingSpaceReservationEntity parkingSpaceReservationEntity);

    ParkingSpaceReservationEntity map(ParkingSpaceReservationDto parkingSpaceReservationDto);
}
