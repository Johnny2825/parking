package ru.example.micro.parking.service.kafka.mailsender;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.example.micro.parking.model.MailMessageDto;

import java.util.UUID;

import static ru.example.micro.parking.model.Constant.MESSAGE_GUID_HEADER;

/**
 * @author Tarkhov Evgeniy
 */
@Component
@RequiredArgsConstructor
public class MailSenderProducerImpl implements MailSenderProducer {
    private static final Logger LOGGER = LogManager.getLogger(MailSenderProducerImpl.class);

    private final StreamBridge streamBridge;

    @Override
    public void sendMessage(MailMessageDto mailMessageDto) {
        try {
            Message<MailMessageDto> message = buildMessage(mailMessageDto);
            streamBridge.send("mail-topic-out-0", message);
        } catch (Exception exception) {
            LOGGER.error("sendMessage() Произошла ошибка во время отправки сообщения. Error: {}", exception.getMessage());
        }
    }

    private Message<MailMessageDto> buildMessage(MailMessageDto mailMessageDto) {
        return MessageBuilder.withPayload(mailMessageDto)
                .setHeader(MESSAGE_GUID_HEADER, UUID.randomUUID().toString())
                .build();
    }
}
