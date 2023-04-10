package ru.example.micro.parking.service.parkingspace.activity;

import ru.example.micro.parking.model.ParkingSpaceActivityDto;

import java.util.List;

/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingSpaceActivityService {

    List<ParkingSpaceActivityDto> findAllByParkingSpaceIds(List<Long> parkingSpaceIdList);
}
