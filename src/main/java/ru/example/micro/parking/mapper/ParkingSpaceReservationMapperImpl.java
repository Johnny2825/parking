package ru.example.micro.parking.mapper;

import org.springframework.stereotype.Component;
import ru.example.micro.parking.controller.dto.ParkingSpaceReservationDto;
import ru.example.micro.parking.entity.ParkingSpaceReservationEntity;

import static java.util.Objects.isNull;

/**
 * @author Tarkhov Evgeniy
 */
@Component
public class ParkingSpaceReservationMapperImpl implements ParkingSpaceReservationMapper {

    @Override
    public ParkingSpaceReservationDto map(ParkingSpaceReservationEntity parkingSpaceReservationEntity) {
        if (isNull(parkingSpaceReservationEntity)) {
            return null;
        }
        return ParkingSpaceReservationDto.builder()
                .id(parkingSpaceReservationEntity.getParkingSpaceId())
                .parkingSpaceId(parkingSpaceReservationEntity.getParkingSpaceId())
                .userId(parkingSpaceReservationEntity.getUserId())
                .timeFrom(parkingSpaceReservationEntity.getTimeFrom())
                .timeTo(parkingSpaceReservationEntity.getTimeTo())
                .build();
    }

    @Override
    public ParkingSpaceReservationEntity map(ParkingSpaceReservationDto parkingSpaceReservationDto) {
        if (isNull(parkingSpaceReservationDto)) {
            return null;
        }
        return ParkingSpaceReservationEntity.builder()
                .parkingSpaceId(parkingSpaceReservationDto.getParkingSpaceId())
                .userId(parkingSpaceReservationDto.getUserId())
                .timeFrom(parkingSpaceReservationDto.getTimeFrom())
                .timeTo(parkingSpaceReservationDto.getTimeTo())
                .build();
    }
}
