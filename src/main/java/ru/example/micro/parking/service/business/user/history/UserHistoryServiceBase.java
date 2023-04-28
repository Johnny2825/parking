package ru.example.micro.parking.service.business.user.history;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.mapper.UserHistoryMapper;
import ru.example.micro.parking.model.dto.UserHistoryCreate;
import ru.example.micro.parking.model.dto.UserHistoryResponse;
import ru.example.micro.parking.repository.UserHistoryRepository;

import static ru.example.micro.parking.entity.QUserHistoryEntity.userHistoryEntity;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class UserHistoryServiceBase implements UserHistoryService {

    private final UserHistoryRepository userHistoryRepository;
    private final UserHistoryMapper userHistoryMapper;

    @Override
    public Page<UserHistoryResponse> findAllByUserId(
            @NonNull final Long userId,
            @NonNull final Pageable pageable
    ) {
        var predicate = userHistoryEntity.userId.eq(userId);
        var userHistoryDtoList = userHistoryRepository.findAll(predicate, pageable)
                .stream()
                .map(userHistoryMapper::toResponse)
                .toList();
        return new PageImpl<>(userHistoryDtoList, pageable, userHistoryDtoList.size());
    }

    @Override
    public void addUserHistory(@NonNull final UserHistoryCreate userHistory) {
        userHistoryRepository.save(userHistoryMapper.toEntity(userHistory));
    }
}
