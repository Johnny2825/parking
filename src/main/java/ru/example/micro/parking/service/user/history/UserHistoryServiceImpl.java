package ru.example.micro.parking.service.user.history;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.controller.dto.UserHistoryDto;
import ru.example.micro.parking.entity.QUserHistoryEntity;
import ru.example.micro.parking.mapper.UserHistoryMapper;
import ru.example.micro.parking.repository.UserHistoryRepository;

import java.util.List;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class UserHistoryServiceImpl implements UserHistoryService {

    private final UserHistoryRepository userHistoryRepository;
    private final UserHistoryMapper userHistoryMapper;

    @Override
    public Page<UserHistoryDto> findAllByUserId(@NonNull final Long userId, @NonNull final Pageable pageable) {
        QUserHistoryEntity qUserHistoryEntity = QUserHistoryEntity.userHistoryEntity;
        Predicate predicate = qUserHistoryEntity.userId.eq(userId);
        List<UserHistoryDto> userHistoryDtoList = userHistoryRepository.findAll(predicate, pageable)
                .stream()
                .map(userHistoryMapper::map)
                .toList();
        return new PageImpl<>(userHistoryDtoList, pageable, userHistoryDtoList.size());
    }

    @Override
    public void addUserHistory(@NonNull final UserHistoryDto userHistoryNew) {
        userHistoryRepository.save(userHistoryMapper.map(userHistoryNew));
    }
}