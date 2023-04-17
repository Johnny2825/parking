package ru.example.micro.parking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.micro.parking.entity.TariffEntity;

/**
 * @author Tarkhov Evgeniy
 */
@Repository
public interface TariffRepository extends CrudRepository<TariffEntity, Long> {
}
