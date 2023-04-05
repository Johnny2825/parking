package ru.example.micro.parking.service.parkingspace;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.example.micro.parking.controller.dto.ParkingSpaceDto;

/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingSpaceService {

    Page<ParkingSpaceDto> getAllParkingSpace(Predicate predicate, Pageable pageable);

}
