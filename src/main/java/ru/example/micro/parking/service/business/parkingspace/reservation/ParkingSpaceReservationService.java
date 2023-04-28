package ru.example.micro.parking.service.business.parkingspace.reservation;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.example.micro.parking.model.dto.ParkingSpaceReservationRequest;
import ru.example.micro.parking.model.dto.ParkingSpaceReservationResponse;

import java.util.Optional;

/**
 * @author Tarkhov Evgeniy
 */
public interface ParkingSpaceReservationService {

    Page<ParkingSpaceReservationResponse> findAllReservation(
            @NonNull Predicate predicate,
            @NonNull Pageable pageable
    );

    /**
     * Создание брони
     * @param reservationDto объект брони
     * @return объект созданной брони
     */
    Optional<ParkingSpaceReservationResponse> createReservation(@NonNull ParkingSpaceReservationRequest reservationDto);

    /**
     * Обновление брони
     * @param id идентификатор брони
     * @param reservationDto объект для обновления брони
     * @return объект обновленной брони
     */
    Optional<ParkingSpaceReservationResponse> updateReservation(
            @NonNull Long id,
            @NonNull ParkingSpaceReservationRequest reservationDto
    );

    /**
     * Удаление брони по идентификатору
     * @param id идентификатор брони
     */
    void deleteReservation(@NonNull Long id);

}
