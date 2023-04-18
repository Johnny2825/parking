package ru.example.micro.parking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.example.micro.parking.controller.dto.ParkingSpaceReservationDto;
import ru.example.micro.parking.service.parkingspace.reservation.ParkingSpaceReservationService;

/**
 * @author Tarkhov Evgeniy
 */
@RestController
@RequiredArgsConstructor
public class ParkingSpaceReservationController {

    private final ParkingSpaceReservationService parkingSpaceReservationService;


    @GetMapping("/reservation/byuser/{userId}")
    public Page<ParkingSpaceReservationDto> findAllByUserId(@PathVariable("userId") Long userId,
                                                            Pageable pageable) {
        return parkingSpaceReservationService.findAllReservationByUserId(userId, pageable);
    }

    @GetMapping("/reservation/byparking/{parkingId}")
    public Page<ParkingSpaceReservationDto> findAllByParkingId(@PathVariable("parkingId") Long parkingId,
                                                               Pageable pageable) {
        return parkingSpaceReservationService.findAllReservationByParkingId(parkingId, pageable);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<ParkingSpaceReservationDto> findById(@PathVariable("id") Long id) {
        return parkingSpaceReservationService.findReservationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/reservation")
    public ResponseEntity<ParkingSpaceReservationDto> createReservation(@Valid @RequestBody ParkingSpaceReservationDto parkingSpaceReservationDto) {
        return parkingSpaceReservationService.createReservation(parkingSpaceReservationDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PutMapping("/reservation/{id}")
    public ResponseEntity<ParkingSpaceReservationDto> updateReservation(@PathVariable("id") Long id,
                                                                        @Valid @RequestBody ParkingSpaceReservationDto parkingSpaceReservationDto) {
        return parkingSpaceReservationService.updateReservation(id, parkingSpaceReservationDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<ParkingSpaceReservationDto> deleteReservation(@PathVariable("id") Long id) {
        parkingSpaceReservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
