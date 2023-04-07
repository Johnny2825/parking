package ru.example.micro.parking.service.parking;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.example.micro.parking.controller.dto.ParkingDto;

import java.util.Optional;

/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingService {

    Page<ParkingDto> getAllParking(Predicate predicate, Pageable pageable);

    Optional<ParkingDto> findParkingById(Long parkingId);
}
