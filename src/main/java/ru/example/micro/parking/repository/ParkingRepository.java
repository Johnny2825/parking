package ru.example.micro.parking.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.example.micro.parking.entity.ParkingEntity;

/**
 * @author Tarkhov Evgeniy
 */
@Repository
public interface ParkingRepository extends PagingAndSortingRepository<ParkingEntity, Long>,
        QuerydslPredicateExecutor<ParkingEntity> {
}
