package ru.example.micro.parking.mapper;

import org.springframework.stereotype.Component;
import ru.example.micro.parking.controller.dto.ParkingSpaceDto;
import ru.example.micro.parking.entity.ParkingSpaceEntity;

import static java.util.Objects.isNull;

/**
 * @author Tarkhov Evgeniy
 */
@Component
public class ParkingSpaceMapperImpl implements ParkingSpaceMapper {

    private static final String PLACE_CODE_FORMAT = "%d-%d";

    @Override
    public ParkingSpaceDto map(ParkingSpaceEntity parkingSpaceEntity) {
        if (isNull(parkingSpaceEntity)) {
            return null;
        }
        return ParkingSpaceDto.builder()
                .id(parkingSpaceEntity.getId())
                .parkingId(parkingSpaceEntity.getParkingId())
                .placeCode(String.format(PLACE_CODE_FORMAT, parkingSpaceEntity.getLevel(), parkingSpaceEntity.getSpace()))
                .isEmpty(isNull(parkingSpaceEntity.getUserId()))
                .userId(parkingSpaceEntity.getUserId())
                .build();
    }
}
