package ru.example.micro.parking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.example.micro.parking.entity.ParkingSpaceEntity;
import ru.example.micro.parking.model.dto.ParkingSpaceResponse;

/**
 * @author Tarkhov Evgeniy
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParkingSpaceMapper {

//    private static final String PLACE_CODE_FORMAT = "%d-%d";

    ParkingSpaceResponse toResponse(ParkingSpaceEntity parkingSpaceEntity);
}
