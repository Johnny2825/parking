package ru.example.micro.parking.mapper;

import org.springframework.stereotype.Component;
import ru.example.micro.parking.controller.dto.ParkingSpaceDto;
import ru.example.micro.parking.entity.ParkingSpaceEntity;

import static java.util.Objects.isNull;

/**
 * @author Tarkhov Evgeniy
 */
@Component
public class ParkingPlaceMapperImpl implements ParkingPlaceMapper {

    @Override
    public ParkingSpaceDto map(ParkingSpaceEntity parkingSpaceEntity) {
        if (isNull(parkingSpaceEntity)) {
            return null;
        }
        return ParkingSpaceDto.builder()
                .id(parkingSpaceEntity.getId())
                .code(parkingSpaceEntity.getCode())
                .status(parkingSpaceEntity.getStatus())
                .build();
    }
}
