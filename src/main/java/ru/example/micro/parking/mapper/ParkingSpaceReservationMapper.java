package ru.example.micro.parking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.example.micro.parking.entity.ParkingSpaceReservationEntity;
import ru.example.micro.parking.model.dto.ParkingSpaceReservationRequest;
import ru.example.micro.parking.model.dto.ParkingSpaceReservationResponse;

/**
 * @author Tarkhov Evgeniy
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParkingSpaceReservationMapper {

    ParkingSpaceReservationResponse toResponse(ParkingSpaceReservationEntity parkingSpaceReservationEntity);
    ParkingSpaceReservationEntity toEntity(ParkingSpaceReservationRequest parkingSpaceReservationRequest);
}
