package ru.example.micro.parking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.micro.parking.entity.ParkingSpaceActivityEntity;


/**
 * @author Tarkhov Evgeniy
 */
@Repository
public interface ParkingSpaceActivityRepository extends CrudRepository<ParkingSpaceActivityEntity, Long> {

}
