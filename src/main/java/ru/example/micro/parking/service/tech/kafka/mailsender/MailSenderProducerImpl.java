package ru.example.micro.parking.service.tech.kafka.mailsender;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.example.micro.parking.model.dto.MailMessageOutput;

import java.util.UUID;

import static ru.example.micro.parking.model.Constant.Kafka.MESSAGE_GUID_HEADER;

/**
 * @author Tarkhov Evgeniy
 */
@Component
@RequiredArgsConstructor
public class MailSenderProducerImpl implements MailSenderProducer {

    private final StreamBridge streamBridge;

    @Override
    public void sendMessage(MailMessageOutput mailMessageDto) {
        try {
            Message<MailMessageOutput> message = buildMessage(mailMessageDto);
            streamBridge.send("mail-topic-out-0", message);
        } catch (Exception e) {
        }
    }

    private Message<MailMessageOutput> buildMessage(MailMessageOutput mailMessageDto) {
        return MessageBuilder.withPayload(mailMessageDto)
                .setHeader(MESSAGE_GUID_HEADER, UUID.randomUUID().toString())
                .build();
    }
}
