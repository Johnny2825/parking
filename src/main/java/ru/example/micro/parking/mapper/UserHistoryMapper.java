package ru.example.micro.parking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.example.micro.parking.entity.UserHistoryEntity;
import ru.example.micro.parking.model.dto.UserHistoryCreate;
import ru.example.micro.parking.model.dto.UserHistoryResponse;

/**
 * @author Tarkhov Evgeniy
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserHistoryMapper {

    UserHistoryResponse toResponse(UserHistoryEntity userHistoryEntity);

    UserHistoryEntity toEntity(UserHistoryCreate userHistoryCreate);
}
