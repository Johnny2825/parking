package ru.example.micro.parking.service.kafka.mailsender;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.example.micro.parking.model.MailMessageDto;

import java.util.UUID;

import static ru.example.micro.parking.model.Constant.Kafka.MESSAGE_GUID_HEADER;

/**
 * @author Tarkhov Evgeniy
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class MailSenderProducerImpl implements MailSenderProducer {

    private final StreamBridge streamBridge;

    @Override
    public void sendMessage(MailMessageDto mailMessageDto) {
        try {
            Message<MailMessageDto> message = buildMessage(mailMessageDto);
            streamBridge.send("mail-topic-out-0", message);
        } catch (Exception e) {
            log.error("Произошла ошибка во время отправки сообщения. Error: {}. Объект для отправки: {}",
                    e.getMessage(),
                    mailMessageDto);
        }
    }

    private Message<MailMessageDto> buildMessage(MailMessageDto mailMessageDto) {
        return MessageBuilder.withPayload(mailMessageDto)
                .setHeader(MESSAGE_GUID_HEADER, UUID.randomUUID().toString())
                .build();
    }
}
