package ru.example.micro.parking.mapper;

import org.springframework.stereotype.Component;
import ru.example.micro.parking.controller.dto.ParkingDto;
import ru.example.micro.parking.entity.ParkingEntity;

import static java.util.Objects.isNull;

/**
 * @author Tarkhov Evgeniy
 */
@Component
public class ParkingMapperImpl implements ParkingMapper {
    @Override
    public ParkingDto map(ParkingEntity parkingEntity) {
        if (isNull(parkingEntity)) {
            return null;
        }
        return ParkingDto.builder()
                .id(parkingEntity.getId())
                .name(parkingEntity.getName())
                .address(parkingEntity.getAddress())
                .parkingSpaceAmount(parkingEntity.getParkingSpaceAmount())
                .build();
    }
}
