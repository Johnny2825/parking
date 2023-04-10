package ru.example.micro.parking.mapper;

import org.springframework.stereotype.Component;
import ru.example.micro.parking.entity.ParkingSpaceActivityEntity;
import ru.example.micro.parking.model.ParkingSpaceActivityDto;

import static java.util.Objects.isNull;

/**
 * @author Tarkhov Evgeniy
 */
@Component
public class ParkingSpaceActivityMapperImpl implements ParkingSpaceActivityMapper {

    @Override
    public ParkingSpaceActivityDto map(ParkingSpaceActivityEntity parkingSpaceActivityEntity) {
        if (isNull(parkingSpaceActivityEntity)) {
            return null;
        }
        return ParkingSpaceActivityDto.builder()
                .id(parkingSpaceActivityEntity.getId())
                .parkingSpaceId(parkingSpaceActivityEntity.getParkingSpaceId())
                .userId(parkingSpaceActivityEntity.getUserId())
                .lastUpdate(parkingSpaceActivityEntity.getLastUpdate())
                .build();
    }
}
