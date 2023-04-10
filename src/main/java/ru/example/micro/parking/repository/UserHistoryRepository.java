package ru.example.micro.parking.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.example.micro.parking.entity.UserHistoryEntity;

/**
 * @author Tarkhov Evgeniy
 */
@Repository
public interface UserHistoryRepository extends PagingAndSortingRepository<UserHistoryEntity, Long>,
        QuerydslPredicateExecutor<UserHistoryEntity> {
}
