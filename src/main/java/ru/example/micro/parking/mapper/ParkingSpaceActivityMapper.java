package ru.example.micro.parking.mapper;

import ru.example.micro.parking.entity.ParkingSpaceActivityEntity;
import ru.example.micro.parking.model.ParkingSpaceActivityDto;

/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingSpaceActivityMapper {

    ParkingSpaceActivityDto map(ParkingSpaceActivityEntity parkingSpaceActivityEntity);
}
