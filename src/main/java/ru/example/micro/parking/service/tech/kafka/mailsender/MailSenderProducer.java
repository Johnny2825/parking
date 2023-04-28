package ru.example.micro.parking.service.tech.kafka.mailsender;

import ru.example.micro.parking.model.dto.MailMessageOutput;

/**
 * @author Tarkhov Evgeniy
 */
public interface MailSenderProducer {

    void sendMessage(MailMessageOutput mailMessageDto);
}
