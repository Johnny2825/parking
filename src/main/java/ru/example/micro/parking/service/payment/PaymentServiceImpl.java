package ru.example.micro.parking.service.payment;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.repository.TariffRepository;

import java.math.BigDecimal;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TariffRepository tariffRepository;

    @Override
    public BigDecimal calculatePaymentByMinutes(@Nonnull final Long minutes) {
        BigDecimal tariff = new BigDecimal(10);
        return new BigDecimal(minutes).multiply(tariff);
    }
}
