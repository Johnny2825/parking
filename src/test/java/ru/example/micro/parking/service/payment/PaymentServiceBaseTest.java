package ru.example.micro.parking.service.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.micro.parking.entity.TariffEntity;
import ru.example.micro.parking.repository.TariffRepository;
import ru.example.micro.parking.service.business.payment.PaymentService;
import ru.example.micro.parking.service.business.payment.PaymentServiceBase;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Tarkhov Evgeniy
 */
@ExtendWith(MockitoExtension.class)
class PaymentServiceBaseTest {
//
//    @Mock
//    private TariffRepository tariffRepository;
//    private PaymentService service;
//
//    @BeforeEach
//    void setUp() {
//        service = new PaymentServiceBase(tariffRepository);
//    }
//
//    @Test
//    void calculatePaymentByMinutes() {
//        BigDecimal expected = new BigDecimal(50);
//        Mockito.when(tariffRepository.findAll()).thenReturn(getEntities());
//        BigDecimal result = service.calculatePaymentByMinutes(60L);
////        assertEquals(expected, result);
//    }
//
//
//    private Iterable<TariffEntity> getEntities() {
//       return List.of(new TariffEntity(1L, 15, new BigDecimal(0)),
//               new TariffEntity(2L, 60, new BigDecimal(100)),
//               new TariffEntity(3L, 120, new BigDecimal(50)),
//               new TariffEntity(4L, 180, new BigDecimal(25)));
//    }

}