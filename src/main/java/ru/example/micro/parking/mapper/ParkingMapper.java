package ru.example.micro.parking.mapper;

import ru.example.micro.parking.controller.dto.ParkingDto;
import ru.example.micro.parking.entity.ParkingEntity;

/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingMapper {

    ParkingDto map(ParkingEntity parkingEntity);
}
