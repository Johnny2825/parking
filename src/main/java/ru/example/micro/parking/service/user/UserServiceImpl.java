package ru.example.micro.parking.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.micro.parking.controller.dto.UserDto;
import ru.example.micro.parking.entity.UserEntity;
import ru.example.micro.parking.mapper.UserMapper;
import ru.example.micro.parking.repository.UserRepository;

import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<UserDto> findUserById(Long userId) {
        if (isNull(userId)) {
            return Optional.empty();
        }
        return userRepository.findById(userId).map(userMapper::map);
    }

    @Override
    public Optional<UserDto> createUser(UserDto userDto) {
        if (isNull(userDto)) {
            return Optional.empty();
        }
        UserEntity userEntity = userRepository.save(userMapper.map(userDto));
        return Optional.of(userMapper.map(userEntity));
    }

    @Override
    @Transactional
    public Optional<UserDto> updateUser(Long userId, UserDto userDto) {
        if (isNull(userId) || isNull(userDto)) {
            return Optional.empty();
        }
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setFirstName(userDto.getFirstName());
            userEntity.setLastName(userDto.getLastName());
            userEntity.setEmail(userDto.getEmail());
            return Optional.of(userDto);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserDto> deleteUser(Long userId) {
        if (isNull(userId)) {
            return Optional.empty();
        }
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        userEntityOptional.ifPresent(userEntity -> userEntity.setActive(false));
        return userEntityOptional.map(userMapper::map);
    }
}
