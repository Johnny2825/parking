package ru.example.micro.parking.service.tech.notification.mail;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.service.tech.kafka.mailsender.MailSenderProducer;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailSenderProducer mailSenderProducer;

    @Override
    public void sendMessage(@NonNull final String email, @NonNull final String message) {
        if (StringUtils.isAnyBlank(email, message)) {
            return;
        }
        System.out.println(email + " " + message);
//        mailSenderProducer.sendMessage(MailMessageDto.builder()
//                .address(user.getEmail())
//                .message(String.format(message, user.getFirstName()))
//                .build());
    }
}
