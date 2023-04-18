package ru.example.micro.parking.service.user.history;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.example.micro.parking.controller.dto.UserHistoryDto;
import ru.example.micro.parking.entity.UserHistoryEntity;
import ru.example.micro.parking.mapper.UserHistoryMapper;
import ru.example.micro.parking.repository.UserHistoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Tarkhov Evgeniy
 */
@ExtendWith(MockitoExtension.class)
class UserHistoryServiceImplTest {
    @Mock
    private UserHistoryRepository userHistoryRepository;
    @Mock
    private UserHistoryMapper userHistoryMapper;
    private UserHistoryService service;

    @BeforeEach
    void setUp() {
        service = new UserHistoryServiceImpl(userHistoryRepository,
                userHistoryMapper);
    }

    @Test
    void findAllByUserId() {
        Long userId = 1L;
        List<UserHistoryEntity> entityList = List.of(UserHistoryEntity.builder().id(1L).build(),
                UserHistoryEntity.builder().id(2L).build());
        List<UserHistoryDto> expected = List.of(UserHistoryDto.builder().id(1L).build(),
                UserHistoryDto.builder().id(2L).build());

        when(userHistoryMapper.map(any(UserHistoryEntity.class))).thenAnswer(invocationOnMock -> {
            UserHistoryEntity entity = invocationOnMock.getArgument(0);
            return UserHistoryDto.builder()
                    .id(entity.getId())
                    .build();
        });
        when(userHistoryRepository.findAll(any(Predicate.class) , eq(Pageable.unpaged())))
                .thenReturn(new PageImpl<>(entityList));

        Page<UserHistoryDto> result = service.findAllByUserId(userId, Pageable.unpaged());
        assertEquals(expected, result.getContent());
    }

    @Test
    void addUserHistory() {
        UserHistoryDto userHistoryNew = UserHistoryDto.builder().build();
        when(userHistoryMapper.map(any(UserHistoryDto.class))).thenReturn(UserHistoryEntity.builder().build());
        service.addUserHistory(userHistoryNew);
        verify(userHistoryRepository, times(1)).save(any(UserHistoryEntity.class));
    }
}