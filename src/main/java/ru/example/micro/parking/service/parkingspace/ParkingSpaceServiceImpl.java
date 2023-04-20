package ru.example.micro.parking.service.parkingspace;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.controller.dto.ParkingSpaceDto;
import ru.example.micro.parking.controller.dto.ParkingSpaceUserDto;
import ru.example.micro.parking.controller.dto.UserDto;
import ru.example.micro.parking.controller.dto.UserHistoryDto;
import ru.example.micro.parking.entity.ParkingSpaceEntity;
import ru.example.micro.parking.entity.QParkingSpaceEntity;
import ru.example.micro.parking.exception.ParkingSpaceIsNotEmpty;
import ru.example.micro.parking.exception.ParkingSpaceNotFoundException;
import ru.example.micro.parking.exception.UserNotFoundException;
import ru.example.micro.parking.mapper.ParkingSpaceMapper;
import ru.example.micro.parking.repository.ParkingSpaceRepository;
import ru.example.micro.parking.service.notification.mail.MailService;
import ru.example.micro.parking.service.payment.PaymentService;
import ru.example.micro.parking.service.user.UserService;
import ru.example.micro.parking.service.user.history.UserHistoryService;
import ru.example.micro.parking.utils.DateTimeConverter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.nonNull;
import static ru.example.micro.parking.utils.MessageBuilderUtils.messageUserFinishParking;
import static ru.example.micro.parking.utils.MessageBuilderUtils.messageUserStartParking;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingSpaceMapper parkingSpaceMapper;
    private final UserService userService;
    private final UserHistoryService userHistoryService;
    private final PaymentService paymentService;
    private final MailService mailService;

    @Override
    public Page<ParkingSpaceDto> findAllParkingSpace(final Predicate predicate, final Pageable pageable) {
        List<ParkingSpaceDto> parkingSpaceList = parkingSpaceRepository
                .findAll(predicate, pageable)
                .stream()
                .map(parkingSpaceMapper::map)
                .toList();
        return new PageImpl<>(parkingSpaceList, pageable, parkingSpaceList.size());
    }

    @Override
    public Optional<ParkingSpaceDto> findParkingSpaceById(@NonNull final Long parkingSpaceId,
                                                          @NonNull final Boolean reservationAvailable) {
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
    public Optional<ParkingSpaceDto> startParking(@NonNull final ParkingSpaceUserDto parkingSpaceUser) {
        final UserDto user = getUserById(parkingSpaceUser.getUserId());
        final ParkingSpaceEntity parkingSpaceEntity = checkOpportunityStart(parkingSpaceUser);
        parkingSpaceEntity.setUserId(parkingSpaceUser.getUserId());
        parkingSpaceEntity.setLastUpdate(LocalDateTime.now());
        parkingSpaceRepository.save(parkingSpaceEntity);
        ParkingSpaceDto retVal = parkingSpaceMapper.map(parkingSpaceEntity);
        String message = messageUserStartParking(user.getFirstName(),
                retVal.getPlaceCode(),
                DateTimeConverter.localDateTimeToString(LocalDateTime.now()));
        mailService.sendMessage(user.getEmail(), message);
        return Optional.of(retVal);
    }

    private ParkingSpaceEntity checkOpportunityStart(@NonNull final ParkingSpaceUserDto parkingSpaceUser) throws ParkingSpaceNotFoundException {
         Optional<ParkingSpaceEntity> parkingSpaceOptional = parkingSpaceRepository.findById(parkingSpaceUser.getParkingSpaceId());
         if (parkingSpaceOptional.isEmpty() ||
                 TRUE.equals(parkingSpaceOptional.get().getReservationAvailable())) {
             throw new ParkingSpaceNotFoundException("Не найдено парковочное место с идентификатором " + parkingSpaceUser.getParkingSpaceId());
         }
         final ParkingSpaceEntity parkingSpaceEntity = parkingSpaceOptional.get();
         if (nonNull(parkingSpaceEntity.getUserId())
                && Objects.equals(parkingSpaceUser.getUserId(), parkingSpaceEntity.getUserId())) {
            throw new ParkingSpaceIsNotEmpty(
                    String.format("Парковочное место с идентификатором %s занято пользователем %s",
                            parkingSpaceEntity.getId(),
                            parkingSpaceEntity.getUserId()));
        }
        return parkingSpaceEntity;
    }

    @Override
    public Optional<ParkingSpaceDto> finishParking(@NonNull final ParkingSpaceUserDto parkingSpaceUser) throws ParkingSpaceNotFoundException {
        UserDto user = getUserById(parkingSpaceUser.getUserId());
        ParkingSpaceEntity parkingSpaceEntity = parkingSpaceRepository
                .findByIdAndUserId(parkingSpaceUser.getParkingSpaceId(), parkingSpaceUser.getUserId())
                .orElseThrow(() -> new ParkingSpaceNotFoundException(
                            String.format("Не найдено парковочное место с идентификатором %s и с пользователем %s",
                                    parkingSpaceUser.getParkingSpaceId(), parkingSpaceUser.getUserId())));
        ParkingSpaceDto retVal = updateParkingSpaceData(parkingSpaceEntity);
        parkingSpaceRepository.save(parkingSpaceEntity);
        String message = messageUserFinishParking(user.getFirstName(),
                retVal.getPlaceCode(),
                DateTimeConverter.localDateTimeToString(LocalDateTime.now()));
        mailService.sendMessage(user.getEmail(), message);
        return Optional.of(retVal);
    }


    private ParkingSpaceDto updateParkingSpaceData(@NonNull final ParkingSpaceEntity parkingSpaceEntity) {
        ParkingSpaceDto retVal = parkingSpaceMapper.map(parkingSpaceEntity);
        LocalDateTime timeFrom = parkingSpaceEntity.getLastUpdate();
        LocalDateTime timeTo = LocalDateTime.now();
        parkingSpaceEntity.setUserId(null);
        parkingSpaceEntity.setLastUpdate(timeTo);
        Long minutes = calcMinutes(timeFrom, timeTo);
        BigDecimal payment = paymentService.calculatePaymentByMinutes(minutes);
        retVal.setMinutes(minutes);
        retVal.setPayment(payment);
        retVal.setIsEmpty(true);
        addUserHistory(retVal, timeFrom, timeTo, payment);
        return retVal;
    }

    /**
     *
     * @param timeFrom
     * @param timeTo
     * @return
     */
    private Long calcMinutes(@NonNull final LocalDateTime timeFrom, @NonNull final LocalDateTime timeTo) {
        Duration duration = Duration.between(timeFrom, timeTo);
        return duration.toMinutes();
    }

    /**
     *
     * @param userId
     * @return
     * @throws UserNotFoundException
     */
    private UserDto getUserById(@NonNull final Long userId) throws UserNotFoundException {
        return userService.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("Не найден пользователь по идентификатору " + userId));
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
