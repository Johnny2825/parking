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

    /**
     * Получение всех броней человека по его идентификатору
     * @param userId идентифкатор человека
     * @param pageable объект страницы
     * @return объект страницы со списком броней
     */
    Page<ParkingSpaceReservationDto> findAllReservationByUserId(@NonNull Long userId, @NonNull Pageable pageable);

    /**
     * Получение всех броней по идентификатору парковки
     * @param parkingId идентификатор парковки
     * @param pageable объект страницы
     * @return объект страницы со списком броней
     */
    Page<ParkingSpaceReservationDto> findAllReservationByParkingId(@NonNull Long parkingId, @NonNull Pageable pageable);

    /**
     * Получение брони по индентификатору
     * @param id идентификатор брони
     * @return объект брони
     */
    Optional<ParkingSpaceReservationDto> findReservationById(@NonNull Long id);

    /**
     * Создание брони
     * @param reservationDto объект брони
     * @return объект созданной брони
     */
    Optional<ParkingSpaceReservationDto> createReservation(@NonNull ParkingSpaceReservationDto reservationDto);

    /**
     * Обновление брони
     * @param id идентификатор брони
     * @param reservationDto объект для обновления брони
     * @return объект обновленной брони
     */
    Optional<ParkingSpaceReservationDto> updateReservation(@NonNull Long id, @NonNull ParkingSpaceReservationDto reservationDto);

    /**
     * Удаление брони по идентификатору
     * @param id идентификатор брони
     */
    void deleteReservation(@NonNull Long id);

}
