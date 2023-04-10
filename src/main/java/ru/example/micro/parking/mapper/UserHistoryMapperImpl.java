package ru.example.micro.parking.mapper;

import org.springframework.stereotype.Component;
import ru.example.micro.parking.controller.dto.UserHistoryDto;
import ru.example.micro.parking.entity.UserHistoryEntity;

import static java.util.Objects.isNull;

/**
 * @author Tarkhov Evgeniy
 */
@Component
public class UserHistoryMapperImpl implements UserHistoryMapper {
    @Override
    public UserHistoryDto map(UserHistoryEntity userHistoryEntity) {
        if (isNull(userHistoryEntity)) {
            return null;
        }
        return UserHistoryDto.builder()
                .id(userHistoryEntity.getId())
                .parkingSpaceId(userHistoryEntity.getParkingSpaceId())
                .userId(userHistoryEntity.getUserId())
                .timeFrom(userHistoryEntity.getTimeFrom())
                .timeTo(userHistoryEntity.getTimeTo())
                .payment(userHistoryEntity.getPayment())
                .build();
    }
}
