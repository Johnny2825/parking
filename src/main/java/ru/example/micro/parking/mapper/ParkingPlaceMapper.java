package ru.example.micro.parking.mapper;

import ru.example.micro.parking.controller.dto.ParkingSpaceDto;
import ru.example.micro.parking.entity.ParkingSpaceEntity;


/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingPlaceMapper {

    ParkingSpaceDto map(ParkingSpaceEntity parkingSpaceEntityOptional);
}
