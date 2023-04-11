package ru.example.micro.parking.service.parkingspace;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.example.micro.parking.controller.dto.ParkingSpaceDto;

import java.util.Optional;

/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingSpaceService {

    Page<ParkingSpaceDto> getAllParkingSpace(Predicate predicate, Pageable pageable);

    /**
     * Получение текучщего паркового места по идентификатору пользователя
     * @param userId идентификатор пользователя
     * @return парковочное место
     */
    Optional<ParkingSpaceDto> getParkingSpaceByUserId(@NonNull Long userId);

    Optional<ParkingSpaceDto> startParking(@NonNull ParkingSpaceDto parkingSpaceDto);

    Optional<ParkingSpaceDto> finishParking(@NonNull ParkingSpaceDto parkingSpaceDto);

}
