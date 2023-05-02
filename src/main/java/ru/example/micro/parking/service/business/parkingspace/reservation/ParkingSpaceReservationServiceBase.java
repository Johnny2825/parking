package ru.example.micro.parking.service.business.parkingspace.reservation;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.entity.ParkingSpaceReservationEntity;
import ru.example.micro.parking.exception.ParkingSpaceNotFoundException;
import ru.example.micro.parking.exception.TimeCrossingException;
import ru.example.micro.parking.exception.UserMismatchException;
import ru.example.micro.parking.exception.UserNotFoundException;
import ru.example.micro.parking.mapper.ParkingSpaceReservationMapper;
import ru.example.micro.parking.model.dto.ParkingSpaceReservationRequest;
import ru.example.micro.parking.model.dto.ParkingSpaceReservationResponse;
import ru.example.micro.parking.repository.ParkingSpaceReservationRepository;
import ru.example.micro.parking.service.business.parkingspace.ParkingSpaceService;
import ru.example.micro.parking.service.business.user.UserService;
import ru.example.micro.parking.service.tech.notification.mail.MailService;
import ru.example.micro.parking.utils.DateTimeConverter;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.notEqual;
import static ru.example.micro.parking.entity.QParkingSpaceEntity.parkingSpaceEntity;
import static ru.example.micro.parking.utils.MessageBuilderUtils.messageUserCreateReservation;
import static ru.example.micro.parking.utils.MessageBuilderUtils.messageUserDeleteReservation;
import static ru.example.micro.parking.utils.MessageBuilderUtils.messageUserUpdateReservation;

/**
 * @author Tarkhov Evgeniy
 */
@Service

@RequiredArgsConstructor
public class ParkingSpaceReservationServiceBase implements ParkingSpaceReservationService {
    private final ParkingSpaceReservationRepository parkingSpaceReservationRepository;
    private final ParkingSpaceReservationMapper parkingSpaceReservationMapper;
    private final UserService userService;
    private final MailService mailService;
    private final ParkingSpaceService parkingSpaceService;

    @Override
    public Page<ParkingSpaceReservationResponse> findAllReservation(
            @NonNull final Predicate predicate,
            @NonNull final Pageable pageable
    ) {
        var parkingSpaceReservationRequestList = parkingSpaceReservationRepository
                .findAll(predicate, pageable)
                .stream()
                .map(parkingSpaceReservationMapper::toResponse)
                .toList();
        return new PageImpl<>(parkingSpaceReservationRequestList, pageable, parkingSpaceReservationRequestList.size());
    }


    @Override
    public Optional<ParkingSpaceReservationResponse> createReservation(@NonNull final ParkingSpaceReservationRequest reservationDto) {
        var user = userService.findUserById(reservationDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(reservationDto.getUserId()));
        checkReservationOpportunityForCreate(reservationDto);
        var entityForCreate = parkingSpaceReservationMapper.toEntity(reservationDto);
        var entityCreated = parkingSpaceReservationRepository.save(entityForCreate);
        var message = messageUserCreateReservation(user.getFirstName(),
                entityCreated.getId(),
                entityCreated.getParkingSpaceId().toString(),
                DateTimeConverter.timeToString(entityCreated.getTimeFrom()),
                DateTimeConverter.timeToString(entityCreated.getTimeTo()));
        mailService.sendMessage(user.getEmail(), message);
        return Optional.of(parkingSpaceReservationMapper.toResponse(entityCreated));
    }

    @Override
    public Optional<ParkingSpaceReservationResponse> updateReservation(
            @NonNull final Long id,
            @NonNull final ParkingSpaceReservationRequest reservationDto
    ) {
        var user = userService.findUserById(reservationDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(reservationDto.getUserId()));
        return parkingSpaceReservationRepository.findById(id).map(reservation -> {
            checkReservationOpportunityForUpdate(reservationDto, reservation);
            reservation.setParkingSpaceId(reservationDto.getParkingSpaceId());
            reservation.setTimeFrom(reservationDto.getTimeFrom());
            reservation.setTimeTo(reservationDto.getTimeTo());
            parkingSpaceReservationRepository.save(reservation);
            var message = messageUserUpdateReservation(user.getFirstName(),
                    reservation.getId(),
                    reservation.getParkingSpaceId().toString(),
                    DateTimeConverter.timeToString(reservation.getTimeFrom()),
                    DateTimeConverter.timeToString(reservation.getTimeTo()));
            mailService.sendMessage(user.getEmail(), message);
            return parkingSpaceReservationMapper.toResponse(reservation);
        });
    }

    @Override
    public void deleteReservation(@NonNull final Long id) {
        parkingSpaceReservationRepository.findById(id).ifPresent(reservation -> {
            userService.findUserById(reservation.getUserId())
                    .ifPresent(user -> {
                        var message = messageUserDeleteReservation(user.getFirstName(),
                                reservation.getId(),
                                reservation.getParkingSpaceId().toString(),
                                DateTimeConverter.timeToString(reservation.getTimeFrom()),
                                DateTimeConverter.timeToString(reservation.getTimeTo()));
                        mailService.sendMessage(user.getEmail(), message);
                    });
            parkingSpaceReservationRepository.deleteById(id);
        });
    }

    /**
     * Проверяем возможность обновить бронь
     *
     * @param reservationDto входной объект брони
     * @param reservationEntity существующая бронь
     * @throws TimeCrossingException исключение в случае пересечения с уже существующими бронями
     * @throws UserMismatchException исключение в случае несоответствия пользователя создавшего бронь и обновляющего
     */
    private void checkReservationOpportunityForUpdate(
            @NonNull final ParkingSpaceReservationRequest reservationDto,
            @NonNull final ParkingSpaceReservationEntity reservationEntity
    ) throws TimeCrossingException, UserMismatchException {
        if (notEqual(reservationEntity.getUserId(), reservationDto.getUserId())) {
            throw new UserMismatchException(reservationEntity.getUserId(), reservationDto.getUserId());
        }
        var reservationList = checkReservationOpportunity(reservationDto);
        if (reservationList.size() > 1
                || (reservationList.size() == 1 && notEqual(reservationEntity.getId(), reservationList.get(0).getId()))) {
            throw new TimeCrossingException(
                    DateTimeConverter.timeToString(reservationDto.getTimeFrom()),
                    DateTimeConverter.timeToString(reservationDto.getTimeTo()));
        }
    }

    /**
     * Проверяем возможность создать бронь
     *
     * @param reservationDto входной объект брони
     * @throws TimeCrossingException исключение в случае пересечения с уже существующими бронями
     */
    private void checkReservationOpportunityForCreate(
            @NonNull final ParkingSpaceReservationRequest reservationDto
    ) throws TimeCrossingException {
        var reservationList = checkReservationOpportunity(reservationDto);
        if (CollectionUtils.isNotEmpty(reservationList)) {
            throw new TimeCrossingException(
                    DateTimeConverter.timeToString(reservationDto.getTimeFrom()),
                    DateTimeConverter.timeToString(reservationDto.getTimeTo()));
        }
    }

    /**
     * Получение броней пересекающихся с переданным диапозоном
     * и проверка возможности забронировать данное парковочное место
     *
     * @param reservationDto входной объект брони
     * @return список существующих броней пересекающихся с переданным диапозоном
     * @throws ParkingSpaceNotFoundException исключение в случае если не найдено доступное для бронирования парковочное место по идентификатору
     * @throws TimeCrossingException исключение в случае если время начала брони больше времени окончания брони
     */
    private List<ParkingSpaceReservationEntity> checkReservationOpportunity(
            @NonNull final ParkingSpaceReservationRequest reservationDto
    ) throws ParkingSpaceNotFoundException, TimeCrossingException {
        if (Duration.between(reservationDto.getTimeFrom(), reservationDto.getTimeTo()).toMinutes() < 60) {
            throw new TimeCrossingException(
                    DateTimeConverter.timeToString(reservationDto.getTimeFrom()),
                    DateTimeConverter.timeToString(reservationDto.getTimeTo()));
        }

        var predicate = parkingSpaceEntity.id.eq(reservationDto.getParkingSpaceId())
                .and(parkingSpaceEntity.reservationAvailable.isTrue());
        var parkingSpace = parkingSpaceService.findParkingSpace(predicate, Pageable.unpaged());
        if (parkingSpace.isEmpty()) {
            throw new ParkingSpaceNotFoundException(reservationDto.getParkingSpaceId());
        }
        return parkingSpaceReservationRepository.findByParkingSpaceIdAndTimeRange(
                reservationDto.getParkingSpaceId(),
                reservationDto.getTimeFrom(),
                reservationDto.getTimeTo());
    }
}
