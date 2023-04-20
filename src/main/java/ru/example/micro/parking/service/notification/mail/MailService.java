package ru.example.micro.parking.service.notification.mail;

import org.springframework.lang.NonNull;

/**
 * @author Tarkhov Evgeniy
 */
public interface MailService {
    void sendMessage(@NonNull String email, @NonNull String message);

}
