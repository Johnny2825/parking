package ru.example.micro.parking.service.notification.mail;

import org.springframework.lang.NonNull;
import ru.example.micro.parking.controller.dto.ParkingSpaceReservationDto;
import ru.example.micro.parking.controller.dto.UserDto;

/**
 * @author Tarkhov Evgeniy
 */
public interface MailService {

    void sendMessage(@NonNull UserDto user, @NonNull String message);

    void sendMessage(@NonNull UserDto user,
                              @NonNull ParkingSpaceReservationDto parkingSpaceReservation,
                              @NonNull String message);
}
