package ru.example.micro.parking.service.parkingspace.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.example.micro.parking.controller.dto.ParkingSpaceReservationDto;

import java.util.Optional;

/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingSpaceReservationService {

    Page<ParkingSpaceReservationDto> findAllReservationByUserId(@NonNull Long userId, @NonNull Pageable pageable);

    Page<ParkingSpaceReservationDto> findAllReservationByParkingId(Long parkingId);

    Optional<ParkingSpaceReservationDto> findReservationById(@NonNull Long id);

    Optional<ParkingSpaceReservationDto> createReservation(@NonNull ParkingSpaceReservationDto reservationDto);

    Optional<ParkingSpaceReservationDto> updateReservation(@NonNull Long id, @NonNull ParkingSpaceReservationDto reservationDto);

    void deleteReservation(@NonNull Long id);

}
