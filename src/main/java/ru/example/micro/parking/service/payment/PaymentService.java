package ru.example.micro.parking.service.payment;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;

/**
 * @author Tarkhov Evgeniy
 */
public interface PaymentService {

    BigDecimal calculatePaymentByMinutes(@NonNull Long minutes);
}
