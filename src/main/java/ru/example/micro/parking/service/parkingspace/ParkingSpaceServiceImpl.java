package ru.example.micro.parking.service.parkingspace;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.micro.parking.controller.dto.ParkingSpaceDto;
import ru.example.micro.parking.entity.ParkingSpaceEntity;
import ru.example.micro.parking.entity.QParkingSpaceEntity;
import ru.example.micro.parking.mapper.ParkingSpaceMapper;
import ru.example.micro.parking.repository.ParkingPlaceRepository;
import ru.example.micro.parking.utils.TypeCastUtil;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingPlaceRepository parkingPlaceRepository;
    private final ParkingSpaceMapper parkingSpaceMapper;

    @Override
    public Page<ParkingSpaceDto> getAllParkingSpace(Predicate predicate, Pageable pageable) {
        List<ParkingSpaceDto> parkingSpaceList = parkingPlaceRepository.findAll(predicate, pageable)
                .stream()
                .map(parkingSpaceMapper::map)
                .toList();
        return new PageImpl<>(parkingSpaceList, pageable, parkingSpaceList.size());
    }

    @Override
    @Transactional
    public Optional<ParkingSpaceDto> takeParkingSpace(ParkingSpaceDto parkingSpaceDto) {
        String parkingPlaceCode = parkingSpaceDto.getPlaceCode();
        Integer level = TypeCastUtil.castIntegerSafety(parkingPlaceCode.split("-")[0]);
        Integer place = TypeCastUtil.castIntegerSafety(parkingPlaceCode.split("-")[1]);
        Optional<ParkingSpaceEntity> parkingSpaceOptional = getParkingSpaceEntity(parkingSpaceDto.getParkingId(), level, place);
        //проверить на существование
        if (parkingSpaceOptional.isPresent()) {
            ParkingSpaceEntity entity = parkingSpaceOptional.get();
            if (nonNull(entity.getUserId())) {
                throw new RuntimeException("Place already busy");
            }
            entity.setUserId(parkingSpaceDto.getUserId());
            entity.setTimeLastUpdate(ZonedDateTime.now());
            return Optional.of(parkingSpaceMapper.map(entity));
        }
        return Optional.empty();
    }


    private Optional<ParkingSpaceEntity> getParkingSpaceEntity(Long parkingId, Integer level, Integer place) {
        QParkingSpaceEntity qParking = QParkingSpaceEntity.parkingSpaceEntity;
        Predicate predicate = qParking.parkingId.eq(parkingId)
                .and(qParking.level.eq(level))
                .and(qParking.place.eq(place));
        return parkingPlaceRepository.findOne(predicate);
    }

}
