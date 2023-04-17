package ru.example.micro.parking.service.parkingspace;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.micro.parking.controller.dto.ParkingSpaceDto;
import ru.example.micro.parking.controller.dto.UserHistoryDto;
import ru.example.micro.parking.entity.ParkingSpaceEntity;
import ru.example.micro.parking.entity.QParkingSpaceEntity;
import ru.example.micro.parking.exception.ParkingSpaceIsNotEmpty;
import ru.example.micro.parking.exception.ParkingSpaceNotFoundException;
import ru.example.micro.parking.mapper.ParkingSpaceMapper;
import ru.example.micro.parking.repository.ParkingSpaceRepository;
import ru.example.micro.parking.service.notification.mail.MailService;
import ru.example.micro.parking.service.payment.PaymentService;
import ru.example.micro.parking.service.user.history.UserHistoryService;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingSpaceMapper parkingSpaceMapper;
    private final UserHistoryService userHistoryService;
    private final PaymentService paymentService;
    private final MailService mailService;

    @Override
    public Page<ParkingSpaceDto> findAllParkingSpace(final Predicate predicate, final Pageable pageable) {
        List<ParkingSpaceDto> parkingSpaceList = parkingSpaceRepository.findAll(predicate, pageable).stream().map(parkingSpaceMapper::map).toList();
        return new PageImpl<>(parkingSpaceList, pageable, parkingSpaceList.size());
    }

    @Override
    public Optional<ParkingSpaceDto> findParkingSpaceById(@NonNull final Long parkingSpaceId, @NonNull final Boolean reservationAvailable) {
        Predicate predicate = QParkingSpaceEntity.parkingSpaceEntity.id.eq(parkingSpaceId)
                .and(QParkingSpaceEntity.parkingSpaceEntity.reservationAvailable.eq(reservationAvailable));
       return parkingSpaceRepository.findOne(predicate).map(parkingSpaceMapper::map);
    }

    @Override
    public Optional<ParkingSpaceDto> findParkingSpaceByUserId(@NonNull final Long userId) {
        Predicate predicate = QParkingSpaceEntity.parkingSpaceEntity.userId.eq(userId);
        return parkingSpaceRepository.findOne(predicate).map(parkingSpaceEntity -> {
            Long minutes = calcMinutes(parkingSpaceEntity.getLastUpdate(), LocalDateTime.now());
            ParkingSpaceDto retVal = parkingSpaceMapper.map(parkingSpaceEntity);
            retVal.setMinutes(minutes);
            retVal.setPayment(paymentService.calculatePaymentByMinutes(minutes));
            return retVal;
        });
    }

    @Override
    @Transactional
    public Optional<ParkingSpaceDto> startParking(@NonNull final ParkingSpaceDto parkingSpaceDto) {
        //TODO а нужна ли проверка на наличие пользователя??
        //TODO сообщение о начале парковки
        //TODO проверка, что это парковка без брони
        Optional<ParkingSpaceEntity> parkingSpaceEntityOptional = parkingSpaceRepository.findById(parkingSpaceDto.getId());
        parkingSpaceEntityOptional.ifPresentOrElse(parkingSpaceEntity -> {
            if (nonNull(parkingSpaceEntity.getUserId())
                    && Objects.equals(parkingSpaceDto.getUserId(), parkingSpaceEntity.getUserId())) {
                throw new ParkingSpaceIsNotEmpty(
                        String.format("Парковочное место с идентификатором %s занято пользователем %s",
                                parkingSpaceEntity.getId(),
                                parkingSpaceEntity.getUserId()));
            }
            parkingSpaceEntity.setUserId(parkingSpaceDto.getUserId());
            parkingSpaceEntity.setLastUpdate(LocalDateTime.now());
        }, () -> {
            throw new ParkingSpaceNotFoundException("Не найдено парковочное место с идентификатором " + parkingSpaceDto.getId());
        });
        parkingSpaceDto.setIsEmpty(false);
        return Optional.of(parkingSpaceDto);
    }

    @Override
    @Transactional
    public Optional<ParkingSpaceDto> finishParking(@NonNull final ParkingSpaceDto parkingSpaceDto) {
        //TODO сообщение о конце парковки
        Optional<ParkingSpaceEntity> parkingSpaceEntityOptional = parkingSpaceRepository
                .findByIdAndUserId(parkingSpaceDto.getId(), parkingSpaceDto.getUserId());
        parkingSpaceEntityOptional.ifPresentOrElse(parkingSpaceEntity -> updateParkingSpaceData(parkingSpaceDto, parkingSpaceEntity),
                () -> {
                    throw new ParkingSpaceNotFoundException(
                            String.format("Не найдено парковочное место с идентификатором %s и с пользователем %s",
                                    parkingSpaceDto.getId(),
                                    parkingSpaceDto.getUserId()));
                });
        parkingSpaceDto.setIsEmpty(true);
        return Optional.of(parkingSpaceDto);
    }

    private void updateParkingSpaceData(@NonNull final ParkingSpaceDto parkingSpaceDto,
                                        @NonNull final ParkingSpaceEntity parkingSpaceEntity) {
        LocalDateTime timeFrom = parkingSpaceEntity.getLastUpdate();
        LocalDateTime timeTo = LocalDateTime.now();
        parkingSpaceEntity.setUserId(null);
        parkingSpaceEntity.setLastUpdate(timeTo);

        Long minutes = calcMinutes(timeFrom, timeTo);
        BigDecimal payment = paymentService.calculatePaymentByMinutes(minutes);
        parkingSpaceDto.setMinutes(minutes);
        parkingSpaceDto.setPayment(payment);
        addUserHistory(parkingSpaceDto, timeFrom, timeTo, payment);
    }

    private Long calcMinutes(@NonNull final LocalDateTime timeFrom, @NonNull final LocalDateTime timeTo) {
        Duration duration = Duration.between(timeFrom, timeTo);
        return duration.toMinutes();
    }

    private void addUserHistory(@NonNull final ParkingSpaceDto parkingSpaceDto,
                                @NonNull final LocalDateTime timeFrom,
                                @NonNull final LocalDateTime timeTo,
                                @NonNull final BigDecimal payment) {
        userHistoryService.addUserHistory(UserHistoryDto.builder()
                .parkingSpaceId(parkingSpaceDto.getId())
                .userId(parkingSpaceDto.getUserId())
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .payment(payment)
                .build());
    }

}
