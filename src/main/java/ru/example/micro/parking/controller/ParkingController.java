package ru.example.micro.parking.controller;

import com.querydsl.core.types.Predicate;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.example.micro.parking.entity.ParkingEntity;
import ru.example.micro.parking.model.dto.ParkingResponse;
import ru.example.micro.parking.service.business.parking.ParkingService;

/**
 * @author Tarkhov Evgeniy
 */
@Validated
@RestController
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;

    @GetMapping("/parking")
    public Page<ParkingResponse> findAll(
            @QuerydslPredicate(root = ParkingEntity.class) Predicate predicate,
            Pageable pageable
    ) {
        return parkingService.getAllParking(predicate, pageable);
    }


    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<ParkingResponse> findParkingById(@PathVariable("parkingId") @Positive Long parkingId) {
        return parkingService.findParkingById(parkingId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
