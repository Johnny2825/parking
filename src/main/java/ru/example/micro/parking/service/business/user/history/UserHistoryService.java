package ru.example.micro.parking.service.business.user.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.example.micro.parking.model.dto.UserHistoryCreate;
import ru.example.micro.parking.model.dto.UserHistoryResponse;

/**
 * @author Tarkhov Evgeniy
 */
public interface UserHistoryService {

    Page<UserHistoryResponse> findAllByUserId(
            @NonNull Long userId,
            @NonNull Pageable pageable
    );

   void addUserHistory(@NonNull UserHistoryCreate userHistory);
}
