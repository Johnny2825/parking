package ru.example.micro.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.example.micro.parking.entity.ParkingEntity;

/**
 * @author Tarkhov Evgeniy
 */
@Repository
public interface ParkingRepository extends JpaRepository<ParkingEntity, Long>,
        QuerydslPredicateExecutor<ParkingEntity> {
}
