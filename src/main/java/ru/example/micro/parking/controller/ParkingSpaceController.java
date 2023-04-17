package ru.example.micro.parking.controller;

import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.example.micro.parking.controller.dto.ParkingSpaceDto;
import ru.example.micro.parking.entity.ParkingSpaceEntity;
import ru.example.micro.parking.service.parkingspace.ParkingSpaceService;

/**
 * @author Tarkhov Evgeniy
 */
@RestController
@RequiredArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    @GetMapping("/parking-space")
    public Page<ParkingSpaceDto> findAll(@QuerydslPredicate(root = ParkingSpaceEntity.class) Predicate predicate,
                                         @PageableDefault(value = 20) Pageable pageable) {
        return parkingSpaceService.findAllParkingSpace(predicate, pageable);
    }

    @GetMapping("/parking-space/byuser/{userId}")
    public ResponseEntity<ParkingSpaceDto> findParkingSpaceByUserId(@PathVariable Long userId) {
        return parkingSpaceService.findParkingSpaceByUserId(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @PostMapping("/parking-space/start-parking")
    public ResponseEntity<ParkingSpaceDto> startParking(@Valid @RequestBody ParkingSpaceDto parkingSpaceDto) {
        return parkingSpaceService.startParking(parkingSpaceDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @PostMapping("/parking-space/finish-parking")
    public ResponseEntity<ParkingSpaceDto> finishParking(@Valid @RequestBody ParkingSpaceDto parkingSpaceDto) {
        return parkingSpaceService.finishParking(parkingSpaceDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    //TODO ключевые точки это:
    // 1) получение всех свободных мест
    // 2) получение всех свободных мест по уровню
    // 3) получение места по пользователю

}
