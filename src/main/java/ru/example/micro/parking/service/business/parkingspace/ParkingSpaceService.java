package ru.example.micro.parking.service.business.parkingspace;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.example.micro.parking.model.dto.ParkingSpaceResponse;
import ru.example.micro.parking.model.dto.ParkingSpaceUserRequest;

import java.util.Optional;

/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingSpaceService {

    Page<ParkingSpaceResponse> findParkingSpace(
            @NonNull Predicate predicate,
            @NonNull Pageable pageable
    );

    Optional<ParkingSpaceResponse> startParking(@NonNull ParkingSpaceUserRequest parkingSpaceUserRequest);

    Optional<ParkingSpaceResponse> finishParking(@NonNull ParkingSpaceUserRequest parkingSpaceUserRequest);

}
