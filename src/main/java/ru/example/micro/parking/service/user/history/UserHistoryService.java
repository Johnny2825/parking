package ru.example.micro.parking.service.user.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.example.micro.parking.controller.dto.UserHistoryDto;

import java.util.Optional;

/**
 * @author Tarkhov Evgeniy
 */
public interface UserHistoryService {

    Page<UserHistoryDto> findAllByUserId(Long userId, Pageable pageable);

    Optional<UserHistoryDto> addUserHistory(UserHistoryDto userHistoryNew);
}
