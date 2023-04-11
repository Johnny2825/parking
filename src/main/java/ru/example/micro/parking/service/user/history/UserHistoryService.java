package ru.example.micro.parking.service.user.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.example.micro.parking.controller.dto.UserHistoryDto;

/**
 * @author Tarkhov Evgeniy
 */
public interface UserHistoryService {

    Page<UserHistoryDto> findAllByUserId(@NonNull Long userId, @NonNull Pageable pageable);

   void addUserHistory(@NonNull UserHistoryDto userHistoryNew);
}
