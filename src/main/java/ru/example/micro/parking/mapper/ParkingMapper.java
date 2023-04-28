package ru.example.micro.parking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.example.micro.parking.entity.ParkingEntity;
import ru.example.micro.parking.model.dto.ParkingResponse;

/**
 * @author Tarkhov Evgeniy
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParkingMapper {

    ParkingResponse toResponse(ParkingEntity parkingEntity);
}
