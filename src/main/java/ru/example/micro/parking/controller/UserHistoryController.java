package ru.example.micro.parking.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.example.micro.parking.model.dto.UserHistoryResponse;
import ru.example.micro.parking.service.business.user.history.UserHistoryService;

/**
 * @author Tarkhov Evgeniy
 */
@Validated
@RestController
@RequiredArgsConstructor
public class UserHistoryController {

    private final UserHistoryService userHistoryService;

    @GetMapping("/user-history/byuser/{userId}")
    public Page<UserHistoryResponse> findAllByUserId(@PathVariable @Positive Long userId,
                                                     Pageable pageable) {
        return userHistoryService.findAllByUserId(userId, pageable);
    }
}
