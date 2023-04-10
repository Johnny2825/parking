package ru.example.micro.parking.service.parkingspace.activity;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.entity.ParkingSpaceActivityEntity;
import ru.example.micro.parking.mapper.ParkingSpaceActivityMapper;
import ru.example.micro.parking.model.ParkingSpaceActivityDto;
import ru.example.micro.parking.repository.ParkingSpaceActivityRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class ParkingSpaceActivityServiceImpl implements ParkingSpaceActivityService {

    private final ParkingSpaceActivityRepository parkingSpaceActivityRepository;
    private final ParkingSpaceActivityMapper parkingSpaceActivityMapper;

    @Override
    public List<ParkingSpaceActivityDto> findAllByParkingSpaceIds(List<Long> parkingSpaceIdList) {
        if (CollectionUtils.isEmpty(parkingSpaceIdList)) {
            return Collections.emptyList();
        }
        List<ParkingSpaceActivityDto> parkingSpaceActivityEntityList = new ArrayList<>(parkingSpaceIdList.size());
        parkingSpaceIdList.forEach(id -> {
            Optional<ParkingSpaceActivityEntity> parkingSpaceActivityOptional = parkingSpaceActivityRepository.findById(id);
            parkingSpaceActivityOptional.ifPresent(e -> parkingSpaceActivityEntityList.add(parkingSpaceActivityMapper.map(e)));
        });
        return parkingSpaceActivityEntityList;
    }
}
