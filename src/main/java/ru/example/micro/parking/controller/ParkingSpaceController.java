package ru.example.micro.parking.controller;

import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.example.micro.parking.model.dto.ParkingSpaceResponse;
import ru.example.micro.parking.model.dto.ParkingSpaceUserRequest;
import ru.example.micro.parking.entity.ParkingSpaceEntity;
import ru.example.micro.parking.service.business.parkingspace.ParkingSpaceService;

/**
 * @author Tarkhov Evgeniy
 */
@Validated
@RestController
@RequiredArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    @GetMapping("/parking-space")
    public Page<ParkingSpaceResponse> findAllParkingSpace(@QuerydslPredicate(root = ParkingSpaceEntity.class) Predicate predicate,
                                              @PageableDefault(value = 20) Pageable pageable) {
        return parkingSpaceService.findParkingSpace(predicate, pageable);
    }

    @PostMapping("/parking-space/start-parking")
    public ResponseEntity<ParkingSpaceResponse> startParking(@Valid @RequestBody ParkingSpaceUserRequest parkingSpaceUserRequest) {
        return parkingSpaceService.startParking(parkingSpaceUserRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @PostMapping("/parking-space/finish-parking")
    public ResponseEntity<ParkingSpaceResponse> finishParking(@Valid @RequestBody ParkingSpaceUserRequest parkingSpaceUserRequest) {
        return parkingSpaceService.finishParking(parkingSpaceUserRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

}
