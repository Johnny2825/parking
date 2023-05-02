package ru.example.micro.parking.service.user;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.micro.parking.model.dto.UserRequest;
import ru.example.micro.parking.entity.UserEntity;
import ru.example.micro.parking.exception.UserExistException;
import ru.example.micro.parking.mapper.UserMapper;
import ru.example.micro.parking.repository.UserRepository;
import ru.example.micro.parking.service.business.user.UserService;
import ru.example.micro.parking.service.business.user.UserServiceBase;
import ru.example.micro.parking.service.tech.notification.mail.MailService;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Tarkhov Evgeniy
 */
@ExtendWith(MockitoExtension.class)
class UserServiceBaseTest {
//
//    private final UserTestData userTestData = new UserTestData();
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private UserMapper userMapper;
//    @Mock
//    private MailService mailService;
//    private UserService service;
//
//    @BeforeEach
//    void setUp() {
//        service = new UserServiceBase(userRepository,
//                userMapper,
//                mailService);
//    }
//
//    @Test
//    void findUserById() {
//        Long userId = 1L;
//        UserEntity userEntity = userTestData.getUserEntity();
//        UserRequest expected = userTestData.getUserDto();
//        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
//        when(userMapper.fromEntity(any(UserEntity.class))).thenAnswer(invocationOnMock -> {
//            UserEntity entity = invocationOnMock.getArgument(0);
//            return UserRequest.builder()
//                    .id(entity.getId())
//                    .firstName(entity.getFirstName())
//                    .lastName(entity.getLastName())
//                    .phone(entity.getPhone())
//                    .email(entity.getEmail())
//                    .build();
//        });
//
//        Optional<UserRequest> result = service.findUserById(userId);
//        assertThat(result)
//                .isPresent()
//                .matches(e -> Objects.equals(expected, e.get()));
//    }
//
//    @Test
//    void createUser_success() {
//        UserRequest userRequestForCreate = userTestData.getUserDtoForCreate();
//        UserRequest expected = userTestData.getUserDto();
//        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocationOnMock -> {
//            UserEntity entity = invocationOnMock.getArgument(0);
//            entity.setId(1L);
//            return entity;
//        });
//        when(userMapper.fromEntity(any(UserEntity.class))).thenAnswer(invocationOnMock -> {
//            UserEntity entity = invocationOnMock.getArgument(0);
//            return UserRequest.builder()
//                    .id(entity.getId())
//                    .firstName(entity.getFirstName())
//                    .lastName(entity.getLastName())
//                    .phone(entity.getPhone())
//                    .email(entity.getEmail())
//                    .build();
//        });
//        when(userMapper.map(any(UserRequest.class))).thenAnswer(invocationOnMock -> {
//            UserRequest userRequest = invocationOnMock.getArgument(0);
//            return UserEntity.builder()
//                    .id(userRequest.getId())
//                    .firstName(userRequest.getFirstName())
//                    .lastName(userRequest.getLastName())
//                    .phone(userRequest.getPhone())
//                    .email(userRequest.getEmail())
//                    .build();
//        });
//
//        Optional<UserRequest> result = service.createUser(userRequestForCreate);
//        assertThat(result)
//                .isPresent()
//                .matches(e -> nonNull(e.get().getId()))
//                .matches(e -> Objects.equals(expected, e.get()));
//        verify(mailService, times(1)).sendMessage(any(UserRequest.class), anyString());
//    }
//
//    @Test
//    void createUser_whenUserWithEmailExist() {
//        UserRequest userRequestForCreate = userTestData.getUserDtoForCreate();
//        when(userRepository.findOne(any(Predicate.class))).thenReturn(Optional.of(userTestData.getUserEntity()));
//        assertThrows(UserExistException.class, () -> service.createUser(userRequestForCreate));
//        verify(mailService, times(0)).sendMessage(any(UserRequest.class), anyString());
//    }
//
//    @Test
//    void updateUser() {
//        Long userId = 1L;
//        UserRequest userRequestForUpdate = userTestData.getUserDto();
//        userRequestForUpdate.setFirstName("Carl");
//        UserRequest expected = userTestData.getUserDto();
//        expected.setFirstName("Carl");
//        when(userRepository.findById(userId)).thenReturn(Optional.of(userTestData.getUserEntity()));
//        when(userMapper.fromEntity(any(UserEntity.class))).thenAnswer(invocationOnMock -> {
//            UserEntity entity = invocationOnMock.getArgument(0);
//            return UserRequest.builder()
//                    .id(entity.getId())
//                    .firstName(entity.getFirstName())
//                    .lastName(entity.getLastName())
//                    .phone(entity.getPhone())
//                    .email(entity.getEmail())
//                    .build();
//        });
//
//        Optional<UserRequest> result = service.updateUser(userId, userRequestForUpdate);
//        assertThat(result)
//                .isPresent()
//                .matches(e -> nonNull(e.get().getId()))
//                .matches(e -> Objects.equals(expected, e.get()));
//        verify(mailService, times(1)).sendMessage(any(UserRequest.class), anyString());
//    }
//
//    @Test
//    void deleteUser() {
//        Long userId = 1L;
//        UserEntity userEntity = userTestData.getUserEntity();
//        UserRequest expected = userTestData.getUserDto();
//        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
//        when(userMapper.fromEntity(any(UserEntity.class))).thenAnswer(invocationOnMock -> {
//            UserEntity entity = invocationOnMock.getArgument(0);
//            return UserRequest.builder()
//                    .id(entity.getId())
//                    .firstName(entity.getFirstName())
//                    .lastName(entity.getLastName())
//                    .phone(entity.getPhone())
//                    .email(entity.getEmail())
//                    .build();
//        });
//        Optional<UserRequest> result = service.deleteUser(userId);
//        assertThat(result)
//                .isPresent()
//                .matches(e -> Objects.equals(expected, e.get()));
//        verify(mailService, times(1)).sendMessage(any(UserRequest.class), anyString());
//    }
}