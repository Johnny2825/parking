package ru.example.micro.parking.mapper;

import ru.example.micro.parking.controller.dto.ParkingSpaceDto;
import ru.example.micro.parking.entity.ParkingSpaceEntity;


/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingSpaceMapper {

    ParkingSpaceDto map(ParkingSpaceEntity parkingSpaceEntityOptional);
}
