package ru.example.micro.parking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.example.micro.parking.entity.ParkingSpaceReservationEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author Tarkhov Evgeniy
 */
@Repository
public interface ParkingSpaceReservationRepository extends JpaRepository<ParkingSpaceReservationEntity, Long> {

    Page<ParkingSpaceReservationEntity> findAllByUserId(Long userId, Pageable pageable);

    @Query(value = "SELECT {res.*} " +
            "FROM parking_space_reservation res " +
            "JOIN parking_space sp ON res.parking_space_id = sp.id " +
            "where sp.parking_id = :parkingId",
            nativeQuery = true)
    Collection<ParkingSpaceReservationEntity> findAllByParkingId(@Param("parkingId") Long parkingId);

    @Query(value = "FROM ParkingSpaceReservationEntity res " +
            "WHERE res.parkingSpaceId = :parkingSpaceId " +
            "AND (:timeFrom < res.timeFrom AND res.timeFrom < :timeTo) " +
            "OR (:timeFrom < res.timeTo AND res.timeTo < :timeTo) " +
            "OR (res.timeFrom < :timeFrom AND :timeFrom < res.timeTo) " +
            "OR (res.timeFrom < :timeTo AND :timeTo < res.timeTo)")
    List<ParkingSpaceReservationEntity> findByParkingSpaceIdAndTimeRange(@Param("parkingSpaceId") Long parkingSpaceId,
                                                                         @Param("timeFrom") LocalDateTime timeFrom,
                                                                         @Param("timeTo") LocalDateTime timeTo);
}
