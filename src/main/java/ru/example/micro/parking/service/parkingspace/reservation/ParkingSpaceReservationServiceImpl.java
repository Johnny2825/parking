package ru.example.micro.parking.service.parkingspace.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.micro.parking.controller.dto.ParkingSpaceDto;
import ru.example.micro.parking.controller.dto.ParkingSpaceReservationDto;
import ru.example.micro.parking.controller.dto.UserDto;
import ru.example.micro.parking.entity.ParkingSpaceReservationEntity;
import ru.example.micro.parking.exception.TimeException;
import ru.example.micro.parking.exception.ParkingSpaceNotFoundException;
import ru.example.micro.parking.exception.UserNotFoundException;
import ru.example.micro.parking.mapper.ParkingSpaceReservationMapper;
import ru.example.micro.parking.repository.ParkingSpaceReservationRepository;
import ru.example.micro.parking.service.notification.mail.MailService;
import ru.example.micro.parking.service.parkingspace.ParkingSpaceService;
import ru.example.micro.parking.service.user.UserService;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_CREATE_RESERVATION;
import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_DELETE_RESERVATION;
import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_UPDATED_RESERVATION;

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
    public Page<ParkingSpaceReservationDto> findAllReservationByParkingId(Long parkingId) {
        List<ParkingSpaceReservationDto> parkingSpaceReservationDtoList = parkingSpaceReservationRepository
                .findAllByParkingId(parkingId)
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
        UserDto user = checkDateRangeAndUserExist(reservationDto);
        checkReservationOpportunity(reservationDto);
        ParkingSpaceReservationEntity entityForCreate = parkingSpaceReservationMapper.map(reservationDto);
        ParkingSpaceReservationEntity entityCreated = parkingSpaceReservationRepository.save(entityForCreate);
        mailService.sendMessage(user, USER_CREATE_RESERVATION);
        return Optional.of(parkingSpaceReservationMapper.map(entityCreated));
    }

    //поработать над обновлением. Проверка, что это тот же пользователь и проверка диапозона дат
    @Override
    @Transactional
    public Optional<ParkingSpaceReservationDto> updateReservation(@NonNull final Long id,
                                                                  @NonNull final ParkingSpaceReservationDto reservationDto) {
        UserDto user = checkDateRangeAndUserExist(reservationDto);
        checkReservationOpportunity(reservationDto);
        return parkingSpaceReservationRepository.findById(id).map(reservationEntity -> {
            if (Objects.equals(reservationEntity.getUserId(), user.getId())) {
                throw new RuntimeException("Другой пользователь");
            }
            reservationEntity.setParkingSpaceId(reservationDto.getParkingSpaceId());
            reservationEntity.setTimeFrom(reservationDto.getTimeFrom());
            reservationEntity.setTimeTo(reservationDto.getTimeTo());
            mailService.sendMessage(user, USER_UPDATED_RESERVATION);
            return reservationDto;
        });
    }

    @Override
    public void deleteReservation(@NonNull final Long id) {
        parkingSpaceReservationRepository.findById(id).ifPresent(parkingSpaceReservationEntity -> {
            userService.findUserById(parkingSpaceReservationEntity.getUserId())
                    .ifPresent(userDto -> mailService.sendMessage(userDto, USER_DELETE_RESERVATION));
            parkingSpaceReservationRepository.deleteById(id);
        });
    }

    private UserDto checkDateRangeAndUserExist(@NonNull final ParkingSpaceReservationDto reservationDto) throws UserNotFoundException {
        if (Duration.between(reservationDto.getTimeFrom(), reservationDto.getTimeTo()).toMinutes() < 60 ) {
            throw new TimeException(String.format("Время между началом брони %s и окончанием %s не может быть меньше часа",
                    reservationDto.getTimeFrom(),
                    reservationDto.getTimeTo()));
        }
        return userService.findUserById(reservationDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(String.format("Не найден пользователь с идентификатором %s", reservationDto.getUserId())));
    }

    private void checkReservationOpportunity(@NonNull final ParkingSpaceReservationDto reservationDto) throws ParkingSpaceNotFoundException, TimeException {
        Optional<ParkingSpaceDto> parkingSpaceOptional = parkingSpaceService.findParkingSpaceById(reservationDto.getParkingSpaceId(), true);
        if (parkingSpaceOptional.isEmpty()) {
            throw new ParkingSpaceNotFoundException(
                    String.format("Не найдено парковочное место с идентификатором %s", reservationDto.getParkingSpaceId()));
        }
        Optional<ParkingSpaceReservationEntity> parkingSpaceReservationOptional = parkingSpaceReservationRepository
                .findByParkingSpaceIdAndTimeRange(reservationDto.getParkingSpaceId(),
                        reservationDto.getTimeFrom(),
                        reservationDto.getTimeTo());
        if (parkingSpaceReservationOptional.isPresent()) {
            throw new TimeException(
                    String.format("Невозможно забронировать на указанное время. Пересечение с другим диапазоном времени"));
        }
    }
}
