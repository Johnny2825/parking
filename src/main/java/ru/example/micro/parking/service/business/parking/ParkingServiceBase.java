package ru.example.micro.parking.service.business.parking;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.mapper.ParkingMapper;
import ru.example.micro.parking.model.dto.ParkingResponse;
import ru.example.micro.parking.repository.ParkingRepository;

import java.util.Optional;

import static ru.example.micro.parking.entity.QParkingEntity.parkingEntity;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class ParkingServiceBase implements ParkingService {
    private final ParkingRepository parkingRepository;
    private final ParkingMapper parkingMapper;

    @Override
    public Page<ParkingResponse> getAllParking(
            @NonNull final Predicate predicate,
            @NonNull final Pageable pageable
    ) {
        var parkingRequestList = parkingRepository.findAll(predicate, pageable)
                .stream()
                .map(parkingMapper::toResponse)
                .toList();
        return new PageImpl<>(parkingRequestList, pageable, parkingRequestList.size());
    }

    @Override
    public Optional<ParkingResponse> findParkingById(@NonNull final Long parkingId) {
        var predicate = parkingEntity.id.eq(parkingId);
        return parkingRepository.findOne(predicate).map(parkingMapper::toResponse);
    }
}
