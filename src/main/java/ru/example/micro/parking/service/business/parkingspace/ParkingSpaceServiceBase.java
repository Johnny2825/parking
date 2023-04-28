package ru.example.micro.parking.service.business.parkingspace;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.entity.ParkingSpaceEntity;
import ru.example.micro.parking.exception.ParkingSpaceNotEmptyException;
import ru.example.micro.parking.exception.ParkingSpaceNotFoundException;
import ru.example.micro.parking.exception.UserNotFoundException;
import ru.example.micro.parking.mapper.ParkingSpaceMapper;
import ru.example.micro.parking.model.dto.ParkingSpaceResponse;
import ru.example.micro.parking.model.dto.ParkingSpaceUserRequest;
import ru.example.micro.parking.model.dto.UserHistoryCreate;
import ru.example.micro.parking.model.dto.UserResponse;
import ru.example.micro.parking.repository.ParkingSpaceRepository;
import ru.example.micro.parking.service.business.payment.PaymentService;
import ru.example.micro.parking.service.business.user.UserService;
import ru.example.micro.parking.service.business.user.history.UserHistoryService;
import ru.example.micro.parking.service.tech.notification.mail.MailService;
import ru.example.micro.parking.utils.DateTimeConverter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static ru.example.micro.parking.utils.MessageBuilderUtils.messageUserFinishParking;
import static ru.example.micro.parking.utils.MessageBuilderUtils.messageUserStartParking;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceBase implements ParkingSpaceService {
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingSpaceMapper parkingSpaceMapper;
    private final UserService userService;
    private final UserHistoryService userHistoryService;
    private final PaymentService paymentService;
    private final MailService mailService;

    @Override
    public Page<ParkingSpaceResponse> findParkingSpace(
            @NonNull final Predicate predicate,
            @NonNull final Pageable pageable
    ) {
        var parkingSpaceList = parkingSpaceRepository
                .findAll(predicate, pageable)
                .stream()
                .map(parkingSpaceMapper::toResponse)
                .toList();
        return new PageImpl<>(parkingSpaceList, pageable, parkingSpaceList.size());
    }

    @Override
    public Optional<ParkingSpaceResponse> startParking(@NonNull final ParkingSpaceUserRequest parkingSpaceUser) {
        var user = getUserById(parkingSpaceUser.getUserId());
        var parkingSpaceEntity = checkOpportunityStart(parkingSpaceUser);

        parkingSpaceEntity.setUserId(parkingSpaceUser.getUserId());
        parkingSpaceEntity.setLastUpdate(LocalDateTime.now());
        parkingSpaceRepository.save(parkingSpaceEntity);

        var retVal = parkingSpaceMapper.toResponse(parkingSpaceEntity);
        var message = messageUserStartParking(user.getFirstName(),
                retVal.getPlaceCode(),
                DateTimeConverter.timeToString(LocalDateTime.now()));
        mailService.sendMessage(user.getEmail(), message);
        return Optional.of(retVal);
    }

    private ParkingSpaceEntity checkOpportunityStart(
            @NonNull final ParkingSpaceUserRequest parkingSpaceUser
    ) throws ParkingSpaceNotFoundException {
         var parkingSpaceOptional = parkingSpaceRepository.findById(parkingSpaceUser.getParkingSpaceId());
         if (parkingSpaceOptional.isEmpty() || parkingSpaceOptional.get().getReservationAvailable()) {
             throw new ParkingSpaceNotFoundException(parkingSpaceUser.getParkingSpaceId());
         }
         var parkingSpaceEntity = parkingSpaceOptional.get();
         if (nonNull(parkingSpaceEntity.getUserId())) {
            throw new ParkingSpaceNotEmptyException(parkingSpaceEntity.getId(), parkingSpaceEntity.getUserId());
        }
        return parkingSpaceEntity;
    }

    @Override
    public Optional<ParkingSpaceResponse> finishParking(
            @NonNull final ParkingSpaceUserRequest parkingSpaceUser
    ) throws ParkingSpaceNotFoundException {
        var user = getUserById(parkingSpaceUser.getUserId());
        ParkingSpaceEntity parkingSpaceEntity = parkingSpaceRepository
                .findByIdAndUserId(parkingSpaceUser.getParkingSpaceId(), parkingSpaceUser.getUserId())
                .orElseThrow(() -> new ParkingSpaceNotFoundException(parkingSpaceUser.getParkingSpaceId()));
        var retVal = updateParkingSpaceData(parkingSpaceEntity);
        parkingSpaceRepository.save(parkingSpaceEntity);
        var message = messageUserFinishParking(user.getFirstName(),
                retVal.getPlaceCode(),
                DateTimeConverter.timeToString(LocalDateTime.now()));
        mailService.sendMessage(user.getEmail(), message);
        return Optional.of(retVal);
    }


    private ParkingSpaceResponse updateParkingSpaceData(@NonNull final ParkingSpaceEntity parkingSpaceEntity) {
        var retVal = parkingSpaceMapper.toResponse(parkingSpaceEntity);
        var timeFrom = parkingSpaceEntity.getLastUpdate();
        var timeTo = LocalDateTime.now();
        parkingSpaceEntity.setUserId(null);
        parkingSpaceEntity.setLastUpdate(timeTo);
        var minutes = calcMinutes(timeFrom, timeTo);
        var payment = paymentService.calculatePaymentByMinutes(minutes);
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
    private Long calcMinutes(
            @NonNull final LocalDateTime timeFrom,
            @NonNull final LocalDateTime timeTo
    ) {
        var duration = Duration.between(timeFrom, timeTo);
        return duration.toMinutes();
    }

    /**
     *
     * @param userId
     * @return
     * @throws UserNotFoundException
     */
    private UserResponse getUserById(@NonNull final Long userId) throws UserNotFoundException {
        return userService.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private void addUserHistory(
            @NonNull final ParkingSpaceResponse parkingSpaceResponse,
            @NonNull final LocalDateTime timeFrom,
            @NonNull final LocalDateTime timeTo,
            @NonNull final BigDecimal payment
    ) {
        userHistoryService.addUserHistory(UserHistoryCreate.builder()
                .parkingSpaceId(parkingSpaceResponse.getId())
                .userId(parkingSpaceResponse.getUserId())
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .payment(payment)
                .build());
    }

}
