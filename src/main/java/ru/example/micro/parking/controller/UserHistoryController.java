package ru.example.micro.parking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.example.micro.parking.controller.dto.UserHistoryDto;
import ru.example.micro.parking.service.user.history.UserHistoryService;

/**
 * @author Tarkhov Evgeniy
 */
@RestController
@RequiredArgsConstructor
public class UserHistoryController {

    private final UserHistoryService userHistoryService;

    @GetMapping("/user-history/byuser/{userId}")
    public Page<UserHistoryDto> findAllByUserId(@PathVariable Long userId,
                                                Pageable pageable) {
        return userHistoryService.findAllByUserId(userId, pageable);
    }
}
