package ru.example.micro.parking.service.parkingspace.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.controller.dto.ParkingSpaceDto;
import ru.example.micro.parking.controller.dto.ParkingSpaceReservationDto;
import ru.example.micro.parking.controller.dto.UserDto;
import ru.example.micro.parking.entity.ParkingSpaceReservationEntity;
import ru.example.micro.parking.exception.ParkingSpaceNotFoundException;
import ru.example.micro.parking.exception.TimeException;
import ru.example.micro.parking.exception.UserMismatchException;
import ru.example.micro.parking.exception.UserNotFoundException;
import ru.example.micro.parking.mapper.ParkingSpaceReservationMapper;
import ru.example.micro.parking.repository.ParkingSpaceReservationRepository;
import ru.example.micro.parking.service.notification.mail.MailService;
import ru.example.micro.parking.service.parkingspace.ParkingSpaceService;
import ru.example.micro.parking.service.user.UserService;
import ru.example.micro.parking.utils.DateTimeConverter;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.notEqual;
import static ru.example.micro.parking.utils.MessageBuilderUtils.messageUserCreateReservation;
import static ru.example.micro.parking.utils.MessageBuilderUtils.messageUserDeleteReservation;
import static ru.example.micro.parking.utils.MessageBuilderUtils.messageUserUpdateReservation;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class ParkingSpaceReservationServiceImpl implements ParkingSpaceReservationService {

    private final ParkingSpaceReservationRepository parkingSpaceReservationRepository;
    private final ParkingSpaceReservationMapper parkingSpaceReservationMapper;
    private final UserService userService;
    private final MailService mailService;
    private final ParkingSpaceService parkingSpaceService;

    @Override
    public Page<ParkingSpaceReservationDto> findAllReservationByUserId(@NonNull final Long userId,
                                                                       @NonNull final Pageable pageable) {
        List<ParkingSpaceReservationDto> parkingSpaceReservationDtoList = parkingSpaceReservationRepository
                .findAllByUserId(userId, pageable)
                .stream()
                .map(parkingSpaceReservationMapper::map)
                .toList();
        return new PageImpl<>(parkingSpaceReservationDtoList, pageable, parkingSpaceReservationDtoList.size());
    }

    @Override
    public Page<ParkingSpaceReservationDto> findAllReservationByParkingId(@NonNull final Long parkingId,
                                                                          @NonNull final Pageable pageable) {
        List<ParkingSpaceReservationDto> parkingSpaceReservationDtoList = parkingSpaceReservationRepository
                .findAllByParkingId(parkingId)  //TODO переделать запрос для работы с page
                .stream()
                .map(parkingSpaceReservationMapper::map)
                .toList();
        return new PageImpl<>(parkingSpaceReservationDtoList, Pageable.unpaged(), parkingSpaceReservationDtoList.size());
    }

    @Override
    public Optional<ParkingSpaceReservationDto> findReservationById(@NonNull final Long id) {
        return parkingSpaceReservationRepository.findById(id).map(parkingSpaceReservationMapper::map);
    }

    @Override
    public Optional<ParkingSpaceReservationDto> createReservation(@NonNull final ParkingSpaceReservationDto reservationDto) {
        UserDto user = userService.findUserById(reservationDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Не найден пользователь с идентификатором %s", reservationDto.getUserId())));
        checkReservationOpportunityForCreate(reservationDto);
        ParkingSpaceReservationEntity entityForCreate = parkingSpaceReservationMapper.map(reservationDto);
        ParkingSpaceReservationEntity entityCreated = parkingSpaceReservationRepository.save(entityForCreate);
        String message = messageUserCreateReservation(user.getFirstName(),
                entityCreated.getId(),
                entityCreated.getParkingSpaceId().toString(),
                DateTimeConverter.localDateTimeToString(entityCreated.getTimeFrom()),
                DateTimeConverter.localDateTimeToString(entityCreated.getTimeTo()));
        mailService.sendMessage(user.getEmail(), message);
        return Optional.of(parkingSpaceReservationMapper.map(entityCreated));
    }

    @Override
    public Optional<ParkingSpaceReservationDto> updateReservation(@NonNull final Long id,
                                                                  @NonNull final ParkingSpaceReservationDto reservationDto) {
        UserDto user = userService.findUserById(reservationDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Не найден пользователь с идентификатором %s", reservationDto.getUserId())));
        return parkingSpaceReservationRepository.findById(id).map(reservation -> {
            checkReservationOpportunityForUpdate(reservationDto, reservation);
            reservation.setParkingSpaceId(reservationDto.getParkingSpaceId());
            reservation.setTimeFrom(reservationDto.getTimeFrom());
            reservation.setTimeTo(reservationDto.getTimeTo());
            parkingSpaceReservationRepository.save(reservation);
            String message = messageUserUpdateReservation(user.getFirstName(),
                    reservation.getId(),
                    reservation.getParkingSpaceId().toString(),
                    DateTimeConverter.localDateTimeToString(reservation.getTimeFrom()),
                    DateTimeConverter.localDateTimeToString(reservation.getTimeTo()));
            mailService.sendMessage(user.getEmail(), message);
            return parkingSpaceReservationMapper.map(reservation);
        });
    }

    @Override
    public void deleteReservation(@NonNull final Long id) {
        parkingSpaceReservationRepository.findById(id).ifPresent(reservation -> {
            userService.findUserById(reservation.getUserId())
                    .ifPresent(user -> {
                        String message = messageUserDeleteReservation(user.getFirstName(),
                                reservation.getId(),
                                reservation.getParkingSpaceId().toString(),
                                DateTimeConverter.localDateTimeToString(reservation.getTimeFrom()),
                                DateTimeConverter.localDateTimeToString(reservation.getTimeTo()));
                        mailService.sendMessage(user.getEmail(), message);
                    });
            parkingSpaceReservationRepository.deleteById(id);
        });
    }

    /**
     * Проверяем возможность обновить бронь
     *
     * @param reservationDto    входной объект брони
     * @param reservationEntity существующая бронь
     * @throws TimeException         исключение в случае пересечения с уже существующими бронями
     * @throws UserMismatchException исключение в случае несоответствия пользователя создавшего бронь и обновляющего
     */
    private void checkReservationOpportunityForUpdate(@NonNull final ParkingSpaceReservationDto reservationDto,
                                                      @NonNull final ParkingSpaceReservationEntity reservationEntity)
            throws TimeException, UserMismatchException {
        if (notEqual(reservationEntity.getUserId(), reservationDto.getUserId())) {
            throw new UserMismatchException(String.format("Не совпадает id пользователя. Существущий: %s Входной: %s.",
                    reservationEntity.getUserId(),
                    reservationDto.getUserId()));
        }
        List<ParkingSpaceReservationEntity> reservationList = checkReservationOpportunity(reservationDto);
        if (reservationList.size() > 1
                || (reservationList.size() == 1 && notEqual(reservationEntity.getId(), reservationList.get(0).getId()))) {
            throw new TimeException("Невозможно забронировать на указанное время. Пересечение с другим диапазоном времени");
        }
    }

    /**
     * Проверяем возможность создать бронь
     *
     * @param reservationDto входной объект брони
     * @throws TimeException исключение в случае пересечения с уже существующими бронями
     */
    private void checkReservationOpportunityForCreate(@NonNull final ParkingSpaceReservationDto reservationDto) throws TimeException {
        List<ParkingSpaceReservationEntity> reservationList = checkReservationOpportunity(reservationDto);
        if (CollectionUtils.isNotEmpty(reservationList)) {
            throw new TimeException("Невозможно забронировать на указанное время. Пересечение с другим диапазоном времени");
        }
    }

    /**
     * Получение броней пересекающихся с переданным диапозоном
     *
     * @param reservationDto входной объект брони
     * @return список существующих броней пересекающихся с переданным диапозоном
     * @throws ParkingSpaceNotFoundException исключение в случае если не найдено доступное для бронирования парковочное место по идентификатору
     * @throws TimeException                 исключение в случае если время начала брони больше времени окончания брони
     */
    private List<ParkingSpaceReservationEntity> checkReservationOpportunity(@NonNull final ParkingSpaceReservationDto reservationDto)
            throws ParkingSpaceNotFoundException, TimeException {
        if (Duration.between(reservationDto.getTimeFrom(), reservationDto.getTimeTo()).toMinutes() < 60) {
            throw new TimeException(String.format("Время между началом брони %s и окончанием %s не может быть меньше часа",
                    reservationDto.getTimeFrom(),
                    reservationDto.getTimeTo()));
        }
        Optional<ParkingSpaceDto> parkingSpaceOptional = parkingSpaceService
                .findParkingSpaceById(reservationDto.getParkingSpaceId(), true);
        if (parkingSpaceOptional.isEmpty()) {
            throw new ParkingSpaceNotFoundException(
                    String.format("Не найдено парковочное место доступное для бронирования с идентификатором %s", reservationDto.getParkingSpaceId()));
        }
        return parkingSpaceReservationRepository.findByParkingSpaceIdAndTimeRange(reservationDto.getParkingSpaceId(),
                reservationDto.getTimeFrom(),
                reservationDto.getTimeTo());
    }
}
