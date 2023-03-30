package ru.example.micro.parking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.micro.parking.entity.UserEntity;

/**
 * @author Tarkhov Evgeniy
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
