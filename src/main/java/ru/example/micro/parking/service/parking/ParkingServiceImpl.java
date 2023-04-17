package ru.example.micro.parking.service.parking;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.controller.dto.ParkingDto;
import ru.example.micro.parking.entity.QParkingEntity;
import ru.example.micro.parking.mapper.ParkingMapper;
import ru.example.micro.parking.repository.ParkingRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;
    private final ParkingMapper parkingMapper;

    @Override
    public Page<ParkingDto> getAllParking(Predicate predicate, Pageable pageable) {
        List<ParkingDto> parkingDtoList = parkingRepository.findAll(predicate, pageable)
                .stream()
                .map(parkingMapper::map)
                .toList();
        return new PageImpl<>(parkingDtoList, pageable, parkingDtoList.size());
    }

    @Override
    public Optional<ParkingDto> findParkingById(Long parkingId) {
        if (isNull(parkingId)) {
            return Optional.empty();
        }
        Predicate predicate = QParkingEntity.parkingEntity.id.eq(parkingId);
        return parkingRepository.findOne(predicate).map(parkingMapper::map);
    }
}
