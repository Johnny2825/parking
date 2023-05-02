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
import ru.example.micro.parking.model.dto.UserRequest;
import ru.example.micro.parking.model.dto.UserResponse;
import ru.example.micro.parking.service.business.user.UserService;

/**
 * @author Tarkhov Evgeniy
 */
@Validated
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable("userId") @Positive Long userId) {
        return userService.findUserById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("userId") @Positive Long userId,
                                                  @Valid @RequestBody UserRequest userRequest) {
        return userService.updateUser(userId, userRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable("userId") @Positive Long userId) {
        return userService.deleteUser(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
