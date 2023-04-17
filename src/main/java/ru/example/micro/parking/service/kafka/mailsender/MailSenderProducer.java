package ru.example.micro.parking.service.kafka.mailsender;

import ru.example.micro.parking.model.MailMessageDto;

/**
 * @author Tarkhov Evgeniy
 */
public interface MailSenderProducer {

    void sendMessage(MailMessageDto mailMessageDto);
}
