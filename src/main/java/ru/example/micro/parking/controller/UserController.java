package ru.example.micro.parking.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.example.micro.parking.controller.dto.UserDto;
import ru.example.micro.parking.service.user.UserService;

/**
 * @author Tarkhov Evgeniy
 */
@Validated
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("userId") @Positive Long userId) {
        return userService.findUserById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") @Positive Long userId,
                                              @Valid @RequestBody UserDto userDto) {
        return userService.updateUser(userId, userDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("userId") @Positive Long userId) {
        return userService.deleteUser(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
