package ru.example.micro.parking.service.business.parking;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.example.micro.parking.model.dto.ParkingResponse;

import java.util.Optional;

/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingService {

    Page<ParkingResponse> getAllParking(
            @NonNull Predicate predicate,
            @NonNull Pageable pageable
    );

    Optional<ParkingResponse> findParkingById(@NonNull Long parkingId);
}
