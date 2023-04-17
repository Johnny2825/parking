package ru.example.micro.parking.service.notification.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.controller.dto.ParkingSpaceReservationDto;
import ru.example.micro.parking.controller.dto.UserDto;
import ru.example.micro.parking.model.MailMessageDto;
import ru.example.micro.parking.service.kafka.mailsender.MailSenderProducer;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailSenderProducer mailSenderProducer;

    @Override
    public void sendMessage(@NonNull final UserDto user, @NonNull final String message) {
        if (StringUtils.isAnyBlank(user.getEmail(), message)) {
            log.warn("Email или сообщение пустое. Email: {}, Сообщение: {}", user.getEmail(), message);
            return;
        }
        System.out.println(user + " " + message);
//        mailSenderProducer.sendMessage(MailMessageDto.builder()
//                .address(user.getEmail())
//                .message(String.format(message, user.getFirstName()))
//                .build());
    }

    @Override
    public void sendMessage(@NonNull final UserDto user,
                            @NonNull final ParkingSpaceReservationDto parkingSpaceReservation,
                            @NonNull final String message) {

    }
}
