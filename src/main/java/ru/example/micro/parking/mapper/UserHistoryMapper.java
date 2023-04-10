package ru.example.micro.parking.mapper;

import ru.example.micro.parking.controller.dto.UserHistoryDto;
import ru.example.micro.parking.entity.UserHistoryEntity;

/**
 * @author Tarkhov Evgeniy
 */
public interface UserHistoryMapper {

    UserHistoryDto map(UserHistoryEntity userHistoryEntity);
}
