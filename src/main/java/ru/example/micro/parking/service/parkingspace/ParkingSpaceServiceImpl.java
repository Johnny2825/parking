package ru.example.micro.parking.service.parkingspace;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.controller.dto.ParkingSpaceDto;
import ru.example.micro.parking.mapper.ParkingSpaceMapper;
import ru.example.micro.parking.repository.ParkingPlaceRepository;

import java.util.List;

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

}
