package ru.example.micro.parking.service.business.payment;

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
public class PaymentServiceBase implements PaymentService {

    private final TariffRepository tariffRepository;

    @Override
    public BigDecimal calculatePaymentByMinutes(@Nonnull final Long minutes) {
//        Long localMinutes = minutes;
//        BigDecimal payment = new BigDecimal(0);
//        Iterable<TariffEntity> tariffs = tariffRepository.findAll();
//        for (TariffEntity tariff : tariffs) {
//            localMinutes = localMinutes - tariff.getMinutesAmount();
//            if (localMinutes >= 0) {
//                payment = payment.add(tariff.getPayment());
//            }
//        }
        return new BigDecimal(minutes).multiply(BigDecimal.TEN);
    }
}
