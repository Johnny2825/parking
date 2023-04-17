package ru.example.micro.parking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.micro.parking.model.MailMessageDto;
import ru.example.micro.parking.service.kafka.mailsender.MailSenderProducer;

/**
 * @author Tarkhov Evgeniy
 */
@RestController
@RequiredArgsConstructor
public class TestController {

    private final MailSenderProducer mailSenderProducer;


    @GetMapping("/test-kafka")
    public ResponseEntity<String> testKafka() {
        MailMessageDto mail = MailMessageDto.builder()
                .address("bob@mail.ru")
                .message("Hello Bob")
                .build();
        mailSenderProducer.sendMessage(mail);
        return ResponseEntity.ok().build();
    }
}
