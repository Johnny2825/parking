package ru.example.micro.parking.service.parkingplace;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.example.micro.parking.controller.dto.ParkingSpaceDto;

/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingPlaceService {

    Page<ParkingSpaceDto> getAllParkingSpace(Predicate predicate, Pageable pageable);

}
