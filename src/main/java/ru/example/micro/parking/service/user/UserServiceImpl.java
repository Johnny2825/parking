package ru.example.micro.parking.service.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.micro.parking.controller.dto.UserDto;
import ru.example.micro.parking.entity.UserEntity;
import ru.example.micro.parking.mapper.UserMapper;
import ru.example.micro.parking.repository.UserRepository;

import java.util.Optional;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<UserDto> findUserById(@NonNull final Long userId) {
        return userRepository.findById(userId).map(userMapper::map);
    }

    @Override
    public Optional<UserDto> createUser(@NonNull final UserDto userDto) {
        UserEntity userEntityForCreate = userMapper.map(userDto);
        userEntityForCreate.setActive();
        UserEntity userEntityCreated = userRepository.save(userEntityForCreate);
        return Optional.of(userMapper.map(userEntityCreated));
    }

    @Override
    @Transactional
    public Optional<UserDto> updateUser(@NonNull final Long userId,
                                        @NonNull final UserDto userDto) {
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
    public Optional<UserDto> deleteUser(@NonNull final Long userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        userEntityOptional.ifPresent(UserEntity::softDeleted);
        return userEntityOptional.map(userMapper::map);
    }
}
