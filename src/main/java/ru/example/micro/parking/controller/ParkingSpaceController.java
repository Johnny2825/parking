package ru.example.micro.parking.controller;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
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
        return parkingSpaceService.getAllParkingSpace(predicate, pageable);
    }


//    @GetMapping("/parking-space/level/{id}")
//    public Page<ParkingSpaceDto> findAllByLevel(@QuerydslPredicate(root = ParkingSpaceEntity.class) Predicate predicate,
//                                         @PageableDefault(value = 20) Pageable pageable) {
//        return parkingSpaceService.getAllParkingSpace(predicate, pageable);
//    }
//
//    @GetMapping("/parking-space")
//    public Page<ParkingSpaceDto> findAllFree(@QuerydslPredicate(root = ParkingSpaceEntity.class) Predicate predicate,
//                                         @PageableDefault(value = 20) Pageable pageable) {
//        return parkingSpaceService.getAllParkingSpace(predicate, pageable);
//    }

}
