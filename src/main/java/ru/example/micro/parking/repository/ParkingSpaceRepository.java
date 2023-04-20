package ru.example.micro.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.example.micro.parking.entity.ParkingSpaceEntity;

import java.util.Optional;

/**
 * @author Tarkhov Evgeniy
 */
@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpaceEntity, Long>,
        QuerydslPredicateExecutor<ParkingSpaceEntity> {

    Optional<ParkingSpaceEntity> findByIdAndUserId(Long parkingSpaceId, Long userId);

}
