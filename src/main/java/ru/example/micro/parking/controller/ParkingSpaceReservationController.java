package ru.example.micro.parking.controller;

import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.example.micro.parking.entity.ParkingSpaceReservationEntity;
import ru.example.micro.parking.model.dto.ParkingSpaceReservationRequest;
import ru.example.micro.parking.model.dto.ParkingSpaceReservationResponse;
import ru.example.micro.parking.service.business.parkingspace.reservation.ParkingSpaceReservationService;

/**
 * @author Tarkhov Evgeniy
 */
@Validated
@RestController
@RequiredArgsConstructor
public class ParkingSpaceReservationController {

    private final ParkingSpaceReservationService parkingSpaceReservationService;


    @GetMapping("/reservation")
    public Page<ParkingSpaceReservationResponse> findAllByUserId(
            @QuerydslPredicate(root = ParkingSpaceReservationEntity.class) Predicate predicate,
            Pageable pageable) {
        return parkingSpaceReservationService.findAllReservation(predicate, pageable);
    }

    @PostMapping("/reservation")
    public ResponseEntity<ParkingSpaceReservationResponse> createReservation(
            @Valid @RequestBody ParkingSpaceReservationRequest parkingSpaceReservationRequest
    ) {
        return parkingSpaceReservationService.createReservation(parkingSpaceReservationRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PutMapping("/reservation/{id}")
    public ResponseEntity<ParkingSpaceReservationResponse> updateReservation(
            @PathVariable("id") @Positive Long id,
            @Valid @RequestBody ParkingSpaceReservationRequest parkingSpaceReservationRequest
    ) {
        return parkingSpaceReservationService.updateReservation(id, parkingSpaceReservationRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<ParkingSpaceReservationResponse> deleteReservation(@PathVariable("id") @Positive Long id) {
        parkingSpaceReservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
